package com.vacinas.ap2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @NotEmpty(message = "O logradouro em endereço não pode está em branco!")
    @NotNull(message = "O logradouro em endereço não pode ser nulo!")
    private String logradouro;
    @NotNull(message = "O número em endereço não pode está nulo!")
    private Integer numero;
    @NotNull(message = "O bairro em endereço não pode está nulo.")
    @NotEmpty(message = "O bairro em endereço não pode está em branco!")
    private String bairro;
    @NotNull(message = "O município em endereço não pode está nulo!")
    @NotEmpty(message = "O município em endereço não pode está em branco!")
    private String municipio;

    @NotNull(message = "O estado em endereço não pode está nulo!")
    @NotEmpty(message = "O estado em endereço não pode está em branco!")
    private String estado;
}
