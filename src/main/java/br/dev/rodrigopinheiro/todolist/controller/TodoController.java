package br.dev.rodrigopinheiro.todolist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.rodrigopinheiro.todolist.entity.Todo;
import br.dev.rodrigopinheiro.todolist.service.TodoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping
  public ResponseEntity<List<Todo>> create(@RequestBody @Valid Todo todo) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(todoService.create(todo));
  }

  @GetMapping
  public List<Todo> list() {
    return todoService.list();
  }

  @PutMapping("{id}")
  List<Todo> update(@PathVariable Long id, @RequestBody Todo todo) {
    return todoService.update(id, todo);
  }

  @DeleteMapping("{id}")
  public List<Todo> delete(@PathVariable("id") Long id) {
    return todoService.delete(id);
  }

}
