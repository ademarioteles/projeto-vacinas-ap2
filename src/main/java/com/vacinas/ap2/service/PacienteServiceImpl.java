package com.vacinas.ap2.service;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.enums.Estados;
import com.vacinas.ap2.enums.Sexo;
import com.vacinas.ap2.exceptions.*;
import com.vacinas.ap2.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PacienteServiceImpl implements PacienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHandlerException.class);
    private static final SimpleDateFormat FormatDate = new SimpleDateFormat("yyyy-MM-dd");
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
        if (pacienteEncontrado == null || obterTodos().isEmpty() || obterTodos() == null) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        }
        LOGGER.info("Paciente com id " + id + " encontrado!");
        return pacienteEncontrado;
    }

    @Override
    public void inserir(Paciente paciente) {
        if (cpfVerificador(paciente)) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException("Cpf já cadastrado ou formato não permitido!");
        }
        testeCampos(paciente);
        pacienteRepository.insert(paciente);
        LOGGER.info("Paciente com id " + paciente.getId() + " inserido com sucesso!");


    }

    @Override
    public void editarPorId(String id, Paciente paciente) {
        paciente.setId(id);
        this.editar(paciente);
        LOGGER.info("Paciente com id " + id + " editado com sucesso!");
    }

    @Override
    public void editarParcialPorId(String id, Paciente paciente) {
        paciente.setId(id);
        this.editarParcial(paciente);
        LOGGER.info("Paciente com id " + id + " editado com sucesso!");
    }

    @Override
    public void editar(Paciente pacienteEditar) {
        Paciente pacienteEncontrado = this.obterPorId(pacienteEditar.getId());

        if (pacienteEncontrado == null) {
            throw new PacientNotFoundException("Paciente não encontrado, informe o identificador!");
        }

        pacienteEditar = verificarPacienteTodos(pacienteEditar, pacienteEncontrado);

        if (!pacienteEditar.equals(pacienteEncontrado)) {
            pacienteRepository.save(pacienteEditar);
            LOGGER.info("Paciente com id " + pacienteEditar.getId() + " editado com sucesso!");
        }

    }

    @Override
    public void editarParcial(Paciente paciente) {
        Paciente pacienteEncontrado = this.obterPorId(paciente.getId());

        if (paciente == null) {
            throw new PacientNotFoundException("Paciente não encontrado!");
        } else if (pacienteEncontrado == null) {
            throw new PacientNotFoundException("Paciente não encontrado, informe o identificador!");
        }

        paciente = this.verificarPacienteTodos(paciente, pacienteEncontrado);

        if (!paciente.equals(pacienteEncontrado)) {
            pacienteRepository.save(paciente);
            LOGGER.info("Paciente com id " + paciente.getId() + " editado parcialmente com sucesso!");

        }
    }

    @Override
    public boolean cpfVerificador(Paciente paciente) {
        for (Paciente pacient : this.obterTodos()) {
            if (paciente.getCpf().equals(pacient.getCpf())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void deletePorId(String id) {
        if (id == null) {
            throw new PacientNotFoundException("Paciente não encontrado, informe o identificador!");
        }
        Paciente pacienteRemover = this.obterPorId(id);
        pacienteRepository.deleteById(pacienteRemover.getId());
        LOGGER.info("Paciente com id " + pacienteRemover.getId() + " foi excluído com sucesso!");
    }

    @Override
    public void deletarTodos() {
        if (this.obterTodos().isEmpty()) {
            throw new PacientNotFoundException("Não há pacientes a serem deletados!");
        }
        pacienteRepository.deleteAll();
        LOGGER.info("Todos os pacientes foram deletados!");

    }

    @Override
    public Paciente verificarPacienteTodos(Paciente pacientU, Paciente pacientD) {//Preenche atributos vazios com objetos já existente no banco
        pacientU.setId(pacientD.getId());

        if (pacientU.getNome() == null || pacientU.getNome() == "") {
            pacientU.setNome(pacientD.getNome());
        }

        if (pacientU.getSobrenome() == null || pacientU.getSobrenome() == "") {
            pacientU.setSobrenome(pacientD.getSobrenome());
        }

        if (pacientU.getDataNascimento() == null || pacientU.getDataNascimento() == "") {
            pacientU.setDataNascimento(pacientD.getDataNascimento());
        } else if (!dataVerificador(pacientU)) {
            throw new DateFormatException("Data com formato ou informação não permitida!");
        }

        if (pacientU.getCpf() == null || pacientU.getCpf() == "") {
            pacientU.setCpf(pacientD.getCpf());
        } else if ((cpfVerificador(pacientU) && !pacientU.getCpf().equals(pacientD.getCpf())) || !validadorCpf(pacientU.getCpf())) {
            throw new CPFException("Cpf já cadastrado ou formato não permitido!");
        }

        if (pacientU.getSexo() == null) {
            pacientU.setSexo(pacientD.getSexo());
        }
        pacientU.setSexo(sexoValidador(pacientU));

        if (pacientU.getContato() == null || pacientU.getContato() == "") {
            pacientU.setContato(pacientD.getContato());
        } else if (!telefoneValidador(pacientU)) {
            throw new ContactIncorrectException("O contato telefonico permitido é (11)99456-7890");
        }

        if (pacientU.getEndereco() == null) {
            pacientU.setEndereco(pacientD.getEndereco());
        }

        if (pacientU.getEndereco().getLogradouro() == null) {
            pacientU.getEndereco().setLogradouro((pacientD.getEndereco().getLogradouro()));
        }
        if (pacientU.getEndereco().getNumero() == null) {
            pacientU.getEndereco().setNumero((pacientD.getEndereco().getNumero()));
        }
        if(!numeroValidador(pacientU)){
            throw new NumberIncorrectException("O numero é necessario ser positivo!");
        }

        if (pacientU.getEndereco().getBairro() == null || pacientU.getEndereco().getBairro() == "") {
            pacientU.getEndereco().setBairro((pacientD.getEndereco().getBairro()));
        }
        if (pacientU.getEndereco().getMunicipio() == null || pacientU.getEndereco().getBairro() == "") {
            pacientU.getEndereco().setMunicipio((pacientD.getEndereco().getMunicipio()));
        }
        if (pacientU.getEndereco().getEstado() == null || pacientU.getEndereco().getBairro() == "") {
            pacientU.getEndereco().setEstado((pacientD.getEndereco().getEstado()));
        }
        pacientU.getEndereco().setEstado(estadoValidador(pacientU));

        return pacientU;

    }

    @Override
    public boolean validadorCpf(String cpf) {
        String regex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        String cpfModify = cpf.replaceAll("[^0-9]", "");
        if(cpfModify.length() != 11){
            throw new CPFException("O cpf não possui o numero de digitos necessarios.");
        }
        String digitos = cpfModify.substring(0, 9);
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(digitos.charAt(i))) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }

        if (Integer.parseInt(String.valueOf(cpfModify.charAt(9))) != primeiroDigito) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(String.valueOf(cpfModify.charAt(i))) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) {
            segundoDigito = 0;
        }

        return Pattern.matches(regex, cpf) && cpfModify.length() == 11 &&
                Integer.parseInt(String.valueOf(cpfModify.charAt(10))) == segundoDigito;
    }

    @Override
    public boolean telefoneValidador(Paciente paciente) {
        String phone = paciente.getContato();
        String regex = "^(?:\\+55)?\\s?(?:\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4})$";
        return Pattern.matches(regex, phone);
    }

    @Override
    public boolean dataVerificador(Paciente paciente) {
        try {
            if (FormatDate.parse(FormatDate.format(new Date())).getTime() < FormatDate.parse(paciente.getDataNascimento()).getTime()) {
                return false;
            } else if (!paciente.getDataNascimento().equals(FormatDate.format(FormatDate.parse(paciente.getDataNascimento())))) {
                return false;
            }
        } catch (ParseException parseException) {
            LOGGER.info("Erro ao inserir paciente:" + parseException.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String sexoValidador(Paciente paciente) {
        try {
            return Sexo.valueOf(paciente.getSexo().toLowerCase()).toString();
        } catch (IllegalArgumentException illegal) {
            illegal.getMessage();
            throw new SexoNotAllowedException("O sexo não foi definido corretamente");
        }
    }

    @Override
    public String estadoValidador(Paciente paciente) {
        try {
            return Estados.valueOf(paciente.getEndereco().getEstado().toUpperCase()).toString();
        } catch (IllegalArgumentException illegal) {
            illegal.getMessage();
            throw new StateNotAllowedException("O estado não foi definido corretamente");
        }
    }

    @Override
    public boolean numeroValidador(Paciente paciente) {
        String numero = String.valueOf(paciente.getEndereco().getNumero());
        String regex = "^[1-9]\\d*$";
        return Pattern.matches(regex, numero);
    }

    @Override
    public void testeCampos(Paciente paciente) {
        paciente.getEndereco().setEstado(estadoValidador(paciente));
        if (!numeroValidador(paciente)) {// se o formato do numero estiver não estiver positivo dara uma exceção
            throw new NumberIncorrectException("O numero deve ser positivo!");
        }
        if (cpfVerificador(paciente) ) {//Se o paciente já existe retorna um Bad Request
            throw new CPFException("Cpf já cadastrado ou formato não permitido!");
        }
        if (dataVerificador(paciente)) {
            throw new DateFormatException("Data com formato ou informação não permitida!");
        }
        if (!telefoneValidador(paciente)) {
            throw new ContactIncorrectException("O contato telefonico permitido é (11)99456-7890");
        }
    }

    @Override
    public void inject() {
        Paciente pacient = new Paciente("6556b65c2ba8c674fd37b804", "Pablo", "Santos", "601.439.530-01",
                "1995-01-21", Sexo.masculino.toString(), "(11)99994-5679", new Endereco("av. 7 de setembro", 65, "2 de julho", "Salvador", Estados.BA.toString()));
        Paciente pacientUm = new Paciente("6556b65c2ba8c674fd37b803", "Priscila", "Fernandes", "353.056.010-37",
                "1994-07-21", Sexo.feminino.toString(), "(11)99995-5679", new Endereco("Av. luiz tarquinio", 201, "Centro", "Lauro de Freitas", Estados.BA.toString()));
        Paciente pacientDois = new Paciente("6556b65c2ba8c674fd37b703", "Henrique", "Souto", "002.984.730-38",
                "1994-07-21", Sexo.masculino.toString(), "(11)99994-5678", new Endereco("Av. Olivia palito", 74, "Centro", "Caruaru", Estados.PE.toString()));
        Paciente pacientTres = new Paciente("6556b65c2ba8c674fd37b712", "Washinton", "Flores", "781.112.010-01",
                "1994-07-21", Sexo.masculino.toString(), "(11)99994-5677", new Endereco("Av. Tiete", 74, "São Paulo", "São Paulo", Estados.SP.toString()));
        Paciente pacientQuatro = new Paciente("6556b65c2ba8c674fd37b988", "Felipe", "Marques", "854.972.870-50",
                "1992-07-21", Sexo.feminino.toString(), "(11)99994-5689", new Endereco("Av. Brasil", 23, "Rio de Janeiro", "Rio de Janeiro", Estados.RJ.toString()));
        List<Paciente> pacientesInjectados = new ArrayList<>(Arrays.asList(pacient, pacientUm, pacientDois, pacientTres, pacientQuatro));
        pacienteRepository.saveAll(pacientesInjectados);
    }
}
