package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@NotNull(message = "O endereço do paciente não foi informado!")
    private Endereco endereco;

}
