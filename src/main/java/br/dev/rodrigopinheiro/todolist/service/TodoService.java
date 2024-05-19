package br.dev.rodrigopinheiro.todolist.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.stereotype.Service;

import br.dev.rodrigopinheiro.todolist.entity.Todo;
import br.dev.rodrigopinheiro.todolist.exception.BadRequestException;
import br.dev.rodrigopinheiro.todolist.repository.TodoRepository;

@Service
public class TodoService {
  private TodoRepository repository;

  public TodoService(TodoRepository repository) {
    this.repository = repository;
  }

  public List<Todo> list() {
    Sort sort = Sort.by(Direction.DESC, "prioridade")
        .and(Sort.by(Direction.ASC, "id"));

    return repository.findAll(sort);
  }

  public List<Todo> create(Todo todo) {
    repository.save(todo);
    return list();
  }

  public List<Todo> update(Long id, Todo todo) {
    repository.findById(id).ifPresentOrElse((existingTodo) -> {
      todo.setId(id);
      repository.save(todo);
    }, () -> {
      throw new BadRequestException("Todo %d não existe! ".formatted(id));
    });
    return list();

  }

  public List<Todo> delete(Long id) {
    repository.findById(id).ifPresentOrElse((existingTodo) -> {
      repository.delete(existingTodo);
    }, () -> {
      throw new BadRequestException("Todo %d não existe! ".formatted(id));
    });
    return list();

  }

}
