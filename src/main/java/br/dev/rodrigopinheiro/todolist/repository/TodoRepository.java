package br.dev.rodrigopinheiro.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.dev.rodrigopinheiro.todolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
