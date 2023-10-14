package com.vacinas.ap2;

import com.vacinas.ap2.controller.PacienteController;
import com.vacinas.ap2.entity.Endereco;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

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
        pacient.setNome("teste isCpfFoundException");
        pacient.setSobrenome("barbosa");
        pacient.setDataNascimento("21/01/1991");
        pacient.setSexo("masculino");
        pacient.setContato("(74)99485365");
        pacient.setEndereco( new Endereco("av. 7 de setembro",24,"2 de julho","salvador","BA"));
        pacientes.add(pacient);
        when(pacienteServiceImpl.obterTodos()).thenReturn(pacientes);
        when(pacienteServiceImpl.obterPorId(pacient.getId())).thenReturn(pacient);

    }
    @Test
    void obterTodosController(){
        Assertions.assertEquals(ResponseEntity.status(200).body(pacientes), pacienteControllerInject.obterTodos());

    }
    @Test
    void obterPorIdController(){
        Assertions.assertEquals( ResponseEntity.status(200).body(pacient), pacienteControllerInject.obterPorId(pacient.getId()));

    }
    @Test
    void insercaoPacienteController(){//Retorna umaa exceção por meio validation, caso algum dos campos não forem preenchidos
        pacient.setCpf("");
        Assertions.assertThrows(ConstraintViolationException.class, () -> pacienteController.inserir(pacient));
    }
    @Test
    void cpfExistenteController(){//Retorna uma exceção personalizada em caso do usuario cadastrar um cpf ja existente
        when(pacienteServiceImpl.verificarPaciente(pacient)).thenReturn(true);// Atribui que o usuario ja existe no banco de dados e ser usado no controller do inserir paciente
        Assertions.assertThrows(CPFException.class, () -> pacienteControllerInject.inserir(pacient));//Nesse momento de inserção o usuario já existe
    }
    @Test
    void exiteUsuarioController(){//Retorna uma exceção personalizada ao buscar um usuario não existente
        Assertions.assertThrows(PacientNotFoundException.class, () -> pacienteControllerInject.obterPorId("abcde123456"));
    }

}
