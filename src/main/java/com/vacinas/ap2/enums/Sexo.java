package com.vacinas.ap2.enums;

public enum Sexo {
    masculino("masculino"),
    feminino("feminino");
    private final String sexo;
    Sexo(String sexo){
        this.sexo = sexo;
    }
    @Override
    public String toString(){
        return this.sexo;
    }
}
