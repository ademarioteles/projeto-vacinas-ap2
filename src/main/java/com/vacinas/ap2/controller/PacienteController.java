package com.vacinas.ap2.controller;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.service.PacienteService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @PostMapping("/vacinas/inserir")
    public ResponseEntity inserir(){
        Paciente pacients = new Paciente();
        Endereco endereco = new Endereco("Av. 7 de setembro",23,"Lauro","hello","Bahia");
        pacients.setNome("taysa");
        pacients.setSobrenome("Souza");
        pacients.setCpf("856562314");
        pacients.setDataNascimento("26/08/1992");
        pacients.setSexo("masculino");
        pacients.setContato("(74)96556336");
        pacients.setEndereco(endereco);
        pacienteService.inserir(pacients);

        return ResponseEntity.ok(pacients);
    }
    @GetMapping("/vacinas")
    public ResponseEntity<List<Paciente>> obterTodos(){
        return ResponseEntity.ok(pacienteService.obterTodos());
    }
}
