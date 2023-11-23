package com.vacinas.ap2.exceptions;

import com.vacinas.ap2.config.ResponseEntityExceptionHandler;
import com.vacinas.ap2.entity.Mensagem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GenericHandlerException extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHandlerException.class);

    @ExceptionHandler(CPFException.class)
    protected ResponseEntity handleException(CPFException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção CPFException: " + e.getMessage());
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NumberIncorrectException.class)
    protected ResponseEntity handleException(NumberIncorrectException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção NumberIncorrectException: " + e.getMessage());
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PacientNotFoundException.class)
    protected ResponseEntity handleException(PacientNotFoundException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção PacientNotFoundException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DateFormatException.class)
    protected ResponseEntity handleException(DateFormatException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção DateFutureException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SexoNotAllowedException.class)
    protected ResponseEntity handleException(SexoNotAllowedException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção SexoNotAllowedException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StateNotAllowedException.class)
    protected ResponseEntity handleException(StateNotAllowedException e) {
        Mensagem mensagem = new Mensagem(e.getMessage());
        LOGGER.info("Tratamentação de exceção StateNotAllowedException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Mensagem> message = new ArrayList<>();
        System.out.println("entrou aqui");
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            message.add(new Mensagem(error.getDefaultMessage()));
            LOGGER.info("Tratamento de Exceção MethodArgumentNotValidException: " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request);
    }

}
