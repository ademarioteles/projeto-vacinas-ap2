package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    public List<Paciente> obterTodos() {

        return pacienteRepository.findAll();
    }
    public void inserir(Paciente paciente){
        pacienteRepository.insert(paciente);
    }

    public boolean verificarPaciente(Paciente paciente){
        List<Paciente> obterTodos = obterTodos();
        for(Paciente pacient:obterTodos){
            if(paciente.getCpf().equals(pacient.getCpf())){
                return true;
            }
        }
        return false;
    }

}
