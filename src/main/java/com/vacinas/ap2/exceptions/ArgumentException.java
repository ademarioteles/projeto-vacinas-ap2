package com.vacinas.ap2.exceptions;

import com.vacinas.ap2.entity.Mensagem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ArgumentException extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentException.class);
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
    List<Mensagem> message = new ArrayList<>();
    for(ObjectError error:ex.getBindingResult().getAllErrors()){
        message.add(new Mensagem(error.getDefaultMessage()));
        LOGGER.info("Tratamento de Exceção MethodArgumentNotValidException: " + error.getDefaultMessage());
    }
        return handleExceptionInternal(ex, message,headers,HttpStatus.BAD_REQUEST,request);
    }
}
