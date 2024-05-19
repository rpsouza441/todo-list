package br.dev.rodrigopinheiro.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.dev.rodrigopinheiro.todolist.exception.BadRequestException;

@ControllerAdvice
public class GeneralExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  private ResponseEntity<Object> handeBadRequest(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

}
