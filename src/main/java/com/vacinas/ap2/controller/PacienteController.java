package com.vacinas.ap2.controller;

import com.vacinas.ap2.entity.Mensagem;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.service.PacienteService;
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
    PacienteService pacienteService;

    @PutMapping("/pacientes")
    public ResponseEntity<Paciente> editar(@RequestBody @Valid Paciente pacienteEditar){
        pacienteService.editar(pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(pacienteEditar.getId()));
    }
    @PatchMapping("/pacientes")
    public ResponseEntity<Paciente> editarParcial(@RequestBody  Paciente pacienteEditar){
    pacienteService.editarParcial(pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(pacienteEditar.getId()));
    }
    @PatchMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> editarParcialPorId(@PathVariable String id, @RequestBody  Paciente pacienteEditar){
        pacienteService.editarParcialPorId(id,pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(id));
    }
    @PutMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> editarPorId(@PathVariable String id, @Valid @RequestBody  Paciente pacienteEditar){
        pacienteService.editarPorId(id,pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(id));
    }
    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> inserir(@RequestBody @Valid Paciente paciente) {
        pacienteService.inserir(paciente);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(paciente.getId()));
    }
    @PostMapping("/pacientes/inject")//Responsavel por injectar valores de pacientes
    public ResponseEntity<List<Paciente>> inject() {
        pacienteService.inject();
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterTodos());
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> obterTodos() {
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterTodos());
    }
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> obterPorId(@PathVariable String id){
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(id));
    }

    @DeleteMapping("/pacientes")
    public ResponseEntity<Mensagem> deletar(@RequestBody Paciente pacienteEditar){
        pacienteService.deletePorId(pacienteEditar.getId());
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Paciente deletado com sucesso!"));
    }
    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Mensagem> deletarPorId(@PathVariable String id){
        pacienteService.deletePorId(id);
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Paciente deletado com sucesso!"));
    }
    @DeleteMapping("/pacientes/todos")
    public ResponseEntity<Mensagem> deletarTodos(){
        pacienteService.deletarTodos();
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Todos os pacientes foram deletados!"));
    }
    @GetMapping("/sanhok")
    public ResponseEntity sanhok(){//apenas exibira uma mensagem do em TEXT_PLAIN sobre o trabalho
        return ResponseEntity.status(200)
                .contentType(MediaType.TEXT_PLAIN)
                .body("API de Gerenciamento de Vacinação desenvolvida pela equipe Sanhok para atender aos requisitos do projeto 'Programação Web 2 - Oficial 2'");
    }

}
