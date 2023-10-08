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
@Validated
public class Paciente {
    private String id;
    @NotNull(message = "Nome não pode está nulo.")
    @NotEmpty(message = "Nome não pode está nulo.")
    private String nome;
    @NotNull(message = "Sobrenome não pode está nulo.")
    @NotEmpty(message = "CPF não pode está nulo.")
    private String sobrenome;
    @NotNull(message = "CPF não pode está nulo.")
    @NotEmpty(message = "CPF não pode está nulo.")
    private String cpf;
    @NotNull(message = "Data de Nascimento não pode está nulo.")
    @NotEmpty(message = "Data de Nascimento não pode está nulo.")
    private String dataNascimento;
    @NotNull(message = "Sexo não pode está nulo.")
    @NotEmpty(message = "Sexo não pode está nulo.")
    private String sexo;
    @NotNull(message = "Contato não pode está nulo.")
    @NotEmpty(message = "Contato não pode está nulo.")
    private String contato;
    @NotNull(message = "Endereço não pode está nulo.")
    @NotEmpty(message = "Endereço não pode está nulo.")
    private Endereco endereco;

}
