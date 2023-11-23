package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Paciente;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
public interface PacienteService {

    List<Paciente> obterTodos();
    Paciente obterPorId(String id);
    void inserir(Paciente paciente);
    void editarPorId(String id, Paciente paciente);
    void editarParcialPorId(String id,Paciente paciente);
    void editar(Paciente paciente);
    void editarParcial(Paciente paciente);
    boolean cpfVerificador(Paciente paciente);
    void deletePorId(String id);
    void deletarTodos();
    Paciente verificarPacienteTodos(Paciente pacientU, Paciente pacientD);
    boolean validadorCpf(String cpf);
    boolean validadorTelefone(String phone);
    boolean dataVerificador(Paciente paciente);

    void inject();
}
