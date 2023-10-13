package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PacienteService {

    List<Paciente> obterTodos();
    Paciente obterPorId(String id);
    void inserir(Paciente paciente);
    boolean verificarPaciente(Paciente paciente);
}
