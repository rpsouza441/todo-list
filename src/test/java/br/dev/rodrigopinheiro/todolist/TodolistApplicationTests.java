package br.dev.rodrigopinheiro.todolist;

import static br.dev.rodrigopinheiro.todolist.TestConstants.TODO;
import static br.dev.rodrigopinheiro.todolist.TestConstants.TODOS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.dev.rodrigopinheiro.todolist.entity.Todo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("/remove.sql")
class TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testeCreateToDoSuccess() {
		var todo = new Todo("To-Do 1", "descricao ToDo 1", false, 1);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(todo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());

	}

	@Test
	public void testeCreateToDoFailure() {
		var todo = new Todo("", "", false, 0);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus().isBadRequest();

	}

	@Sql("/import.sql")
	@Test
	public void testUpdateToDoSuccess() {
		var todo = new Todo(TODO.getId(), TODO.getNome() + " extra", TODO.getDescricao() + " extra", !TODO.isRealizado(),
				TODO.getPrioridade() + 1);

		webTestClient
				.put()
				.uri("/todos/" + TODO.getId())
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(5)
				.jsonPath("$[0].nome").isEqualTo(todo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}

	@Test
	public void testUpdateTodoFailure() {
		var idUnexinsting = 1L;
		webTestClient
				.put()
				.uri("/todos/" + idUnexinsting)
				.bodyValue(
						new Todo(idUnexinsting, "", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testDeleteTodoSuccess() {
		webTestClient
				.delete()
				.uri("/todos/" + TODOS.get(0).getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(4)
				.jsonPath("$[0].nome").isEqualTo(TODOS.get(1).getNome())
				.jsonPath("$[0].descricao").isEqualTo(TODOS.get(1).getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(TODOS.get(1).isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(TODOS.get(1).getPrioridade());
	}

	@Test
	public void testDeleteTodoFailure() {
		var idUnexinsting = 1L;
		webTestClient
				.delete()
				.uri("/todos/" + idUnexinsting)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	public void testListTodos() throws Exception {
		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(5)
				.jsonPath("$[0]").isEqualTo(TODOS.get(0))
				.jsonPath("$[1]").isEqualTo(TODOS.get(1))
				.jsonPath("$[2]").isEqualTo(TODOS.get(2))
				.jsonPath("$[3]").isEqualTo(TODOS.get(3))
				.jsonPath("$[4]").isEqualTo(TODOS.get(4));

	}

}