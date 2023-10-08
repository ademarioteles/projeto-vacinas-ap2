package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

private String id;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String dataNascimento;
    private String sexo;
    private String contato;
    private Endereco endereco;

}
