package com.vacinas.ap2.exceptions;

import java.io.Serial;

public class DateFormatException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DateFormatException(String message) {
        super(message);
    }
}
