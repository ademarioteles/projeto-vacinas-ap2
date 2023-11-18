package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    private String id;

    @NotEmpty(message = "O nome do paciente não foi informado!")
    private String nome;

    @NotEmpty(message = "O sobronome do paciente não foi informado!")
    private String sobrenome;

    @NotEmpty(message = "O CPF do paciente não foi informado!")
    private String cpf;

    @NotEmpty(message = "A data de nascimento do paciente não foi informado!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",message = "A data deve ter um formato igual a 2023-07-29")
    private String dataNascimento;

    @NotEmpty(message = "O sexo do paciente não foi informado!")
    private String sexo;

    @NotEmpty(message = "O contato do paciente não foi informado!")
    private String contato;
    //  @NotEmpty(message = "O endereço do paciente não foi informado!")

    @Valid
    @NotNull(message = "O Endereço não pode ser nulo!")
    private Endereco endereco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(nome, paciente.nome) && Objects.equals(sobrenome, paciente.sobrenome) && Objects.equals(cpf, paciente.cpf) && Objects.equals(dataNascimento, paciente.dataNascimento) && Objects.equals(sexo, paciente.sexo) && Objects.equals(contato, paciente.contato) && Objects.equals(endereco, paciente.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sobrenome, cpf, dataNascimento, sexo, contato, endereco);
    }
}
