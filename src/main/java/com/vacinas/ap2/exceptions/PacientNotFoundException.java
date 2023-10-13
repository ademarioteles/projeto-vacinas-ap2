package com.vacinas.ap2.exceptions;

import java.io.Serial;

public class PacientNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PacientNotFoundException(String mensagem) {
        super(mensagem);
    }
}
