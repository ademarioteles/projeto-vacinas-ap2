package com.vacinas.ap2.entity;

import com.vacinas.ap2.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

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

    @CPF(message = "O formato do CPF está incorreto, formato correto é 123.123.145-35.")
    private String cpf;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",message = "A data deve ter um formato igual a 2023-07-29")
    private String dataNascimento;
    @NotNull(message = "O sexo do paciente não foi informado!")
    private String sexo;

    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-\\d{4}$", message = "O contato telefonico permitido é (11)99456-7890")
    private String contato;

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
