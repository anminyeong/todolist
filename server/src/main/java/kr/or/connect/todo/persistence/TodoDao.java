package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.domain.Todo;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public List<Todo> selectAll(){
		return jdbc.query(TodoSqls.SELECT_ALL, rowMapper);
	}
	
	public Todo insert(Todo todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		int id = insertAction.executeAndReturnKey(params).intValue();
		todo.setId(id);
		return todo;
	}
	
	public List<Todo> selectByCompleted(int completed){
 		Map<String, Integer> paramMap = Collections.singletonMap("completed", completed);
 		return jdbc.query(TodoSqls.SELECT_BY_COMPLETED, paramMap, rowMapper);
 	}
 	
 	public int updateById(Todo todo){
 		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
 		return jdbc.update(TodoSqls.UPDATE_BY_ID, params);
 	}
 	
 	public int deleteById(int id){
 		Map<String, Integer> params = Collections.singletonMap("id", id);
 		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
 	}
 	
 	public int deleteCompleted(){
 		Map<String, Integer> paramMap = Collections.singletonMap("completed", 1);
 		return jdbc.update(TodoSqls.DELETE_BY_COMPLETED, paramMap);
  	}
}
