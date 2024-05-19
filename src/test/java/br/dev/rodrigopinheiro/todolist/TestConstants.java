package br.dev.rodrigopinheiro.todolist;

import java.util.ArrayList;
import java.util.List;

import br.dev.rodrigopinheiro.todolist.entity.Todo;

public class TestConstants {
  public static final List<Todo> TODOS = new ArrayList<>() {
    {
      add(new Todo(1L, "ToDo 1", "Descricao 1", false, 1));
      add(new Todo(2L, "To-Do 2", "descricao 2", false, 1));
      add(new Todo(3L, "TO DO 3", "descricao", false, 1));
      add(new Todo(4L, "To Do", "descricao 4", false, 1));
      add(new Todo(5L, "todo5", "descricao", false, 1));

    }
  };

  public static final Todo TODO = TODOS.get(0);
}
