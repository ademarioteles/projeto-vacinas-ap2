package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.enums.Estados;
import com.vacinas.ap2.enums.Sexo;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.exceptions.GenericHandlerException;
import com.vacinas.ap2.exceptions.PacientNotFoundException;
import com.vacinas.ap2.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHandlerException.class);
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
        LOGGER.info("Paciente com id " + id + " encontrado!");
        return pacienteEncontrado;
    }

    @Override
    public void inserir(Paciente paciente) {
        if (verificarPaciente(paciente)) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException("Cpf já cadastrado na base!");
        }
        pacienteRepository.insert(paciente);
        LOGGER.info("Paciente com id " + paciente.getId() + " inserido com sucesso!");

    }

    @Override
    public void editarPorId(String id,Paciente paciente) {
        paciente.setId(id);
        this.editar(paciente);
        LOGGER.info("Paciente com id " + id + " editado com sucesso!");

    }

    @Override
    public void editarParcialPorId(String id,Paciente paciente) {
        paciente.setId(id);
        this.editarParcial(paciente);
        LOGGER.info("Paciente com id " + id + " editado com sucesso!");
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
            LOGGER.info("Paciente com id " + pacienteEditar.getId() + " editado com sucesso!");
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
            LOGGER.info("Paciente com id " + paciente.getId() + " editado parcialmente com sucesso!");

        }
    }

    @Override
    public boolean verificarPaciente(Paciente paciente) {
           for (Paciente pacient : this.obterTodos()) {
               if (paciente.getCpf().equals(pacient.getCpf())) {
                   LOGGER.info("Paciente com id " + paciente.getId() + " já existe em nossa base!");
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
        LOGGER.info("Paciente com id " + pacienteRemover.getId() + " foi excluído com sucesso!");
    }

    @Override
    public void deletarTodos() {
        if(this.obterTodos().isEmpty()){
            throw new PacientNotFoundException("Não há pacientes a serem deletados!");
        }
        pacienteRepository.deleteAll();
        LOGGER.info("Todos os pacientes foram deletados!");

    }

    @Override
    public Paciente CompareEdite(Paciente pacientU, Paciente pacientD) {//Preenche atributos vazios com objetos já existente no banco

        if (pacientU.getNome() == null) {
            pacientU.setNome(pacientD.getNome());
        }if (pacientU.getSobrenome() == null) {
            pacientU.setSobrenome(pacientD.getSobrenome());
        }  if (pacientU.getDataNascimento() == null) {
            pacientU.setDataNascimento(pacientD.getDataNascimento());
        }  if (pacientU.getCpf() == null) {
            pacientU.setCpf(pacientD.getCpf());
        }if (pacientU.getSexo() == null) {
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

    @Override
    public void inject() {
        Paciente pacient = new Paciente("6556b65c2ba8c674fd37b804","Pablo","Santos","12345678917",
                "1995-01-21", Sexo.Masculino,"(74)99485365",new Endereco("av. 7 de setembro",65,"2 de julho","Salvador", Estados.BA));
        Paciente pacientUm = new Paciente("6556b65c2ba8c674fd37b803","Priscila","Fernandes","12345678914",
                "1994-07-21",Sexo.Feminino,"(75)98485365",new Endereco("Av. luiz tarquinio",201,"Centro","Lauro de Freitas",Estados.BA));
        Paciente pacientDois = new Paciente("6556b65c2ba8c674fd37b703","Henrique","Souto","12345674914",
                "1994-07-21",Sexo.Masculino,"(81)98485465",new Endereco("Av. Olivia palito",74,"Centro","Caruaru",Estados.PE));
        Paciente pacientTres = new Paciente("6556b65c2ba8c674fd37b712","Washinton","Flores","12345674935",
                "1994-07-21",Sexo.Masculino,"(11)97485465",new Endereco("Av. Tiete",74,"São Paulo","São Paulo",Estados.SP));
        Paciente pacientQuatro = new Paciente("6556b65c2ba8c674fd37b988","Felipe","Marques","15845672935",
                "1992-07-21",Sexo.Feminino,"(21)97465469",new Endereco("Av. Brasil",23,"Rio de Janeiro","Rio de Janeiro",Estados.RJ));
        List<Paciente> pacientesInjectados = new ArrayList<>(Arrays.asList(pacient,pacientUm,pacientDois,pacientTres,pacientQuatro));
        pacienteRepository.saveAll(pacientesInjectados);
    }
}
