package com.vacinas.ap2.exceptions;

import com.vacinas.ap2.entity.Mensagem;

public class CPFException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CPFException(String mensagem) {
        super(mensagem);
    }
}
