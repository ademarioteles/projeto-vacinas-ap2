package com.vacinas.ap2.exceptions;

import com.vacinas.ap2.entity.Mensagem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericHandlerException extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHandlerException.class);

    @ExceptionHandler(CPFException.class)
    public ResponseEntity handleException(CPFException e) {
        Mensagem mensagem = new Mensagem("O CPF informado já encontra-se cadastrado em nossa base de dados!");
        LOGGER.info("Tratamentação de exceção CPFException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PacientNotFoundException.class)
    public ResponseEntity handleException(PacientNotFoundException e) {
        Mensagem mensagem = new Mensagem("Paciente(s) não encontrado(s)");
        LOGGER.info("Tratamentação de exceção PacientNotFoundException: " + mensagem);
        return new ResponseEntity(mensagem, HttpStatus.NOT_FOUND);
    }
}
