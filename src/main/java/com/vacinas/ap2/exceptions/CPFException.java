package com.vacinas.ap2.exceptions;

import com.vacinas.ap2.entity.Mensagem;

import java.io.Serial;

public class CPFException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CPFException(String mensagem) {
        super(mensagem);
    }
}
