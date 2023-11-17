package com.vacinas.ap2;

import com.vacinas.ap2.controller.PacienteController;
import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Mensagem;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.exceptions.PacientNotFoundException;
import com.vacinas.ap2.repository.PacienteRepository;
import com.vacinas.ap2.service.PacienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PacienteControlerTest {
    @Autowired
    PacienteController pacienteController;
    @Mock
    PacienteServiceImpl pacienteServiceImpl;
    @Mock
    Paciente pacient;
    @Mock
    List<Paciente> pacientes;
    @InjectMocks
    PacienteController pacienteControllerInject;

    @BeforeEach
    void carregarPaciente(){
        pacientes = new ArrayList<>();
        pacient = new Paciente();
        pacient.setId("fasdas5d5");
        pacient.setNome("teste isCpfFoundException");
        pacient.setSobrenome("barbosa");
        pacient.setCpf("5665656566");
        pacient.setDataNascimento("21/01/1991");
        pacient.setSexo("masculino");
        pacient.setContato("(74)99485365");
        pacient.setEndereco( new Endereco("av. 7 de setembro",24,"2 de julho","salvador","BA"));
        pacientes.add(pacient);
        when(pacienteServiceImpl.obterPorId("fasdas5d5")).thenReturn(pacient);
        when(pacienteServiceImpl.obterTodos()).thenReturn(pacientes);
    }
    @Test
    void obterTodosSucessoController(){
        Assertions.assertEquals(ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(pacientes), pacienteControllerInject.obterTodos());

    }
    @Test
    void obterPorIdSucessoController(){
        Assertions.assertEquals( ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(pacient), pacienteControllerInject.obterPorId(pacient.getId()));

    }
    @Test
    void insercaoPacienteErrorController(){//Retorna umaa exceção por meio validation, caso algum dos campos não forem preenchidos
        pacient.setCpf("");
        Assertions.assertThrows(ConstraintViolationException.class, () -> pacienteController.inserir(pacient));
    }

    @Test
    void editarSucessoController(){
        Paciente novoPaciente = new Paciente();
        novoPaciente.setId("fasdas5d5");
        novoPaciente.setNome("Novo valor");
        novoPaciente.setSobrenome("alterado");
        novoPaciente.setCpf("5665656567");
        novoPaciente.setDataNascimento("21/01/1993");
        novoPaciente.setSexo("feminino");
        novoPaciente.setContato("(74)99485365");
        novoPaciente.setEndereco( new Endereco("av. 7 de setembro",24,"2 de julho","salvador","BA"));
        pacienteControllerInject.editar(novoPaciente);
        Assertions.assertEquals(pacienteControllerInject.obterPorId(pacient.getId()),pacienteControllerInject.obterPorId(novoPaciente.getId()));

    }
    @Test
    void editarErrorController(){//Retorna umaa exceção por meio validation, caso algum dos campos não forem preenchidos
        pacient.setCpf("");
        Assertions.assertThrows(ConstraintViolationException.class, () -> pacienteController.editar(pacient));
    }
    @Test
    void editarParcialErrorController(){//Retorna umaa exceção por não encontrar o usuario
        pacient.setId("");
        Assertions.assertThrows(PacientNotFoundException.class, () -> pacienteController.editarParcial(pacient));
    }
    @Test
    void editarParcialSucessoController(){//Mantem as informações já existente e edita apenas o necessario
    pacient.setDataNascimento("21/01/1995");
    pacient.setSexo("feminino");
    pacient.setContato(null);// um dos campos pode ser nulo, menos o id.

    Assertions.assertEquals(ResponseEntity.status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pacienteServiceImpl.obterPorId(pacient.getId())), pacienteControllerInject.editarParcial(pacient));
    }
    @Test
    void removerSucessoController(){
        Assertions.assertEquals(ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("Paciente deletado com sucesso!")),pacienteControllerInject.deletar(pacient));
    }
    @Test

    void testeSanhok(){
        Assertions.assertEquals(ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Mensagem("API de Gerenciamento de Vacinação desenvolvida pela equipe Sanhok para atender aos requisitos do projeto \"Programação Web 2 - Oficial 2")),pacienteControllerInject.sanhok());

    }


}
