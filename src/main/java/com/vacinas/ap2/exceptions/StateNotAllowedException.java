package com.vacinas.ap2.exceptions;

import java.io.Serial;

public class StateNotAllowedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public StateNotAllowedException(String message){
        super(message);

    }
}
