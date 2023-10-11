package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    private String id;

    @NotEmpty(message = "O nome do paciente não foi informado!")
    private String nome;

    @NotEmpty(message = "O sobronome do paciente não foi informado!")
    private String sobrenome;

    @NotEmpty(message = "O CPF do paciente não foi informado!")
    private String cpf;

    @NotEmpty(message = "A data de nascimento do paciente não foi informado!")
    private String dataNascimento;

    @NotEmpty(message = "O sexo do paciente não foi informado!")
    private String sexo;

    @NotEmpty(message = "O contato do paciente não foi informado!")
        private String contato;
    @NotEmpty(message = "O endereço do paciente não foi informado!")
    private Endereco endereco;

}
