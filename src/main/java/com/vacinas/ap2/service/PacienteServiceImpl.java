package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> obterTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente obterPorId(String id) {
        for (Paciente pacient : obterTodos()) {//Como o id é uma string o metodo de busca tem que ser diferente do findById
            if (pacient.getId().equals(id)) {
                return pacient;
            }
        }
        return null;
    }

    @Override
    public void inserir(Paciente paciente) {
        pacienteRepository.insert(paciente);
    }

    @Override
    public boolean verificarPaciente(Paciente paciente) {
        for (Paciente pacient : obterTodos()) {
            if (paciente.getCpf().equals(pacient.getCpf())) {
                return true;
            }
        }
        return false;
    }

}
