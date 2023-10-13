package com.vacinas.ap2.controller;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.service.PacienteService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @PostMapping("/pacientes/inserir")
    public ResponseEntity inserir(@RequestBody @Valid Paciente paciente) {
        if (pacienteService.verificarPaciente(paciente)) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException(null);
        }
        pacienteService.inserir(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> obterTodos() {
        return ResponseEntity.status(200).body(pacienteService.obterTodos());
    }
}
