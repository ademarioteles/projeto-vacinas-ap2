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
    @PatchMapping("/pacientes/{id}")
    public ResponseEntity editarParcialPorId(@PathVariable String id, @RequestBody  Paciente pacienteEditar){
        pacienteService.editarParcialPorId(id,pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(id));
    }
    @PutMapping("/pacientes/{id}")
    public ResponseEntity editarPorId(@PathVariable String id, @RequestBody  Paciente pacienteEditar){
        pacienteService.editarPorId(id,pacienteEditar);
        return  ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pacienteService.obterPorId(id));
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
    public ResponseEntity deletar(@RequestBody Paciente pacienteEditar){
        pacienteService.deletePorId(pacienteEditar.getId());
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Paciente deletado com sucesso!"));
    }
    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity deletarPorId(@PathVariable String id){
        pacienteService.deletePorId(id);
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Paciente deletado com sucesso!"));
    }
    @DeleteMapping("/pacientes/todos")
    public ResponseEntity deletarPorId(){
        pacienteService.deletarTodos();
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Todos os pacientes foram deletados!"));
    }
    @GetMapping("/sanhok")
    public ResponseEntity sanhok(){
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("API de Gerenciamento de Pacientes em Desenvolvimento."));
    }

}
