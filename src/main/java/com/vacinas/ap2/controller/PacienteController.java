package com.vacinas.ap2.controller;


import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.service.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PutMapping("/pacientes")
    public ResponseEntity editar(@RequestBody @Valid Paciente pacienteEditar){
        pacienteService.editar(pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(pacienteEditar.getId()));
    }
    @PatchMapping("/pacientes")
    public ResponseEntity editarParcial(@RequestBody  Paciente pacienteEditar){
    pacienteService.editarParcial(pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(pacienteEditar.getId()));
    }
    @PostMapping("/pacientes/cadastrar")
    public ResponseEntity inserir(@RequestBody @Valid Paciente paciente) {

        pacienteService.inserir(paciente);

        return  ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(paciente.getId()));
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> obterTodos() {
        return  ResponseEntity.status(200)
                .body(pacienteService.obterTodos());
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> obterPorId(@PathVariable String id){
        return  ResponseEntity.status(200)
                .body(pacienteService.obterPorId(id));
    }

}
