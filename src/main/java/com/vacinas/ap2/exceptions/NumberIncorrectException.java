package com.vacinas.ap2.exceptions;

import java.io.Serial;

public class NumberIncorrectException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NumberIncorrectException(String message) {
        super(message);
    }
}
