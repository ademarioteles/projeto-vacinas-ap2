package com.vacinas.ap2.exceptions;

import java.io.Serial;

public class SexoNotAllowedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SexoNotAllowedException(String message){
        super(message);
    }
}
