package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @NotNull(message = "Logradouro não pode está nulo.")
    @NotEmpty(message = "Logradouro não pode está nulo.")
    private String logradouro;
    @NotNull(message = "Número não pode está nulo.")
    @NotEmpty(message = "Número não pode está nulo.")
    private Integer numero;
    @NotNull(message = "Bairro não pode está nulo.")
    @NotEmpty(message = "Bairro não pode está nulo.")
    private String bairro;
    @NotNull(message = "Município não pode está nulo.")
    @NotEmpty(message = "Município não pode está nulo.")
    private String municipio;
    @NotNull(message = "Estado não pode está nulo.")
    @NotEmpty(message = "Estado não pode está nulo.")
    private String estado;

}
