package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.exceptions.PacientNotFoundException;
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
        Paciente pacienteEncontrado = null;
        for (Paciente pacient : obterTodos()) {//Como o id é uma string o metodo de busca tem que ser diferente do findById
            if (pacient.getId().equals(id)) {
                pacienteEncontrado = pacient;
                break;
            }
        }
       if(pacienteEncontrado == null || obterTodos().isEmpty() || obterTodos() == null){
           throw  new PacientNotFoundException("Paciente não encontrado!");
       }
        return pacienteEncontrado;
    }

    @Override
    public void inserir(Paciente paciente) {
        if (verificarPaciente(paciente)) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException("Cpf já cadastrado na base!");
        }
        pacienteRepository.insert(paciente);
    }

    @Override
    public void editarPorId(String id,Paciente paciente) {
        paciente.setId(id);
        this.editar(paciente);
    }

    @Override
    public void editarParcialPorId(String id,Paciente paciente) {
        paciente.setId(id);
        this.editarParcial(paciente);
    }

    @Override
    public void editar(Paciente pacienteEditar) {

        Paciente pacienteEncontrado = this.obterPorId(pacienteEditar.getId());

        if (pacienteEditar == null) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        }else if(pacienteEncontrado == null){
            throw new PacientNotFoundException("Paciente não encontrado, informe o Id!");
        }

        if(!pacienteEditar.equals(pacienteEncontrado)) {
            pacienteRepository.save(pacienteEditar);
        }
    }

    @Override
    public void editarParcial(Paciente paciente) {
        Paciente pacienteEncontrado = this.obterPorId(paciente.getId());

    if (paciente == null) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        }else if(pacienteEncontrado== null){
            throw new PacientNotFoundException("Paciente não encontrado, informe o Id!");
        }

        paciente = this.CompareEdite(paciente,pacienteEncontrado);

        if(!paciente.equals(pacienteEncontrado)) {
            pacienteRepository.save(paciente);
        }
    }

    @Override
    public boolean verificarPaciente(Paciente paciente) {
           for (Paciente pacient : this.obterTodos()) {
               if (paciente.getCpf().equals(pacient.getCpf())) {
                   return true;
               }
           }
        return false;
    }

    @Override
    public void deletePorId(String id) {
        if(id == null){
            throw new PacientNotFoundException("Paciente não encontrado, informe o Id");
        }
       Paciente pacienteRemover =  this.obterPorId(id);
       pacienteRepository.deleteById(pacienteRemover.getId());
    }

    @Override
    public Paciente CompareEdite(Paciente pacientU, Paciente pacientD) {

        if (pacientU.getNome() == null) {
            pacientU.setNome(pacientD.getNome());
        }if (pacientU.getSobrenome() == null) {
            pacientU.setSobrenome(pacientD.getSobrenome());
        }  if (pacientU.getDataNascimento() == null) {
            pacientU.setDataNascimento(pacientD.getDataNascimento());
        } if (pacientU.getSexo() == null) {
            pacientU.setSexo(pacientD.getSexo());
        } if (pacientU.getContato() == null) {
            pacientU.setContato(pacientD.getContato());
        }if(pacientU.getEndereco() == null){
            pacientU.setEndereco(pacientD.getEndereco());
        }if (pacientU.getEndereco().getLogradouro() == null) {
            pacientU.getEndereco().setLogradouro((pacientD.getEndereco().getLogradouro()));
        } if (pacientU.getEndereco().getNumero() == null) {
            pacientU.getEndereco().setNumero((pacientD.getEndereco().getNumero()));
        } if (pacientU.getEndereco().getBairro() == null) {
            pacientU.getEndereco().setBairro((pacientD.getEndereco().getBairro()));
        } if (pacientU.getEndereco().getMunicipio() == null) {
            pacientU.getEndereco().setMunicipio((pacientD.getEndereco().getMunicipio()));
        } if (pacientU.getEndereco().getEstado() == null) {
            pacientU.getEndereco().setEstado((pacientD.getEndereco().getEstado()));
        }
        return pacientU;

    }
}
