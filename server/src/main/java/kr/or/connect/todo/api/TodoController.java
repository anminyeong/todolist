package kr.or.connect.todo.api;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}
	
	@GetMapping
	public Collection<Todo> selectAll(){
		return service.selectAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Todo create(@RequestBody Todo todo) {
		todo.setDate(new Timestamp(new Date().getTime()));
		log.info("Todo created : {}",todo);
		return service.insert(todo);
	}
	
	@GetMapping("/filter/{completed}")	
	public Collection<Todo> selectByCompleted(@PathVariable Integer completed){
	 	return service.selectByCompleted(completed);
	}
	
	@PutMapping("/{id}")	
	@ResponseStatus(HttpStatus.NO_CONTENT)
 	public int update(@PathVariable Integer id, @RequestBody Todo todo){
 		todo.setId(id);
 		return service.updateById(todo);
 	}
 	
 	@DeleteMapping("/{id}")	
 	@ResponseStatus(HttpStatus.NO_CONTENT)
 	public int delete(@PathVariable Integer id){
 		return service.deleteById(id);
 	}
 	
 	@DeleteMapping("/completed-item")	
 	@ResponseStatus(HttpStatus.NO_CONTENT)
 	public int deleteComplied(){
 		return service.deleteCompleted();
 	}
}
