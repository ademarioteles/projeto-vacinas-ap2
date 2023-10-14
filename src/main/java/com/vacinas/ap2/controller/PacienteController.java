package com.vacinas.ap2.controller;


import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.exceptions.PacientNotFoundException;
import com.vacinas.ap2.service.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class PacienteController {
    @Autowired
    PacienteServiceImpl pacienteService;

    @PostMapping("/pacientes/inserir")
    public ResponseEntity inserir(@RequestBody @Valid Paciente paciente) {
        if (pacienteService.verificarPaciente(paciente)) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException("Cpf existente");
        }
        pacienteService.inserir(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> obterTodos() {
        if (pacienteService.obterTodos().isEmpty()) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        }
        return ResponseEntity.status(200).body(pacienteService.obterTodos());
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> obterPorId(@PathVariable String id) {
        if (pacienteService.obterPorId(id) == null) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        }
        return ResponseEntity.status(200).body(pacienteService.obterPorId(id));
    }
}
