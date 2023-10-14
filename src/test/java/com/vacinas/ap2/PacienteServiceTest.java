package com.vacinas.ap2;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.repository.PacienteRepository;
import com.vacinas.ap2.service.PacienteServiceImpl;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PacienteServiceTest {
    @Mock
    PacienteRepository pacienteRepository;
    @Mock
    Paciente pacient;
    @Mock
    List<Paciente> pacientes;
    @InjectMocks
    PacienteServiceImpl pacienteServiceImpl;
    @BeforeEach
    void carregarPaciente(){
        pacientes = new ArrayList<>();
        pacient = new Paciente();
        pacient.setId("f5as6das6d65as6d61");
        pacient.setNome("teste isCpfFoundException");
        pacient.setSobrenome("barbosa");
        pacient.setCpf("12345678910");
        pacient.setDataNascimento("21/01/1991");
        pacient.setSexo("masculino");
        pacient.setContato("(74)99485365");
        pacient.setEndereco( new Endereco("av. 7 de setembro",24,"2 de julho","salvador","BA"));
        when(pacienteRepository.findAll()).thenReturn(pacientes);
        pacientes.add(pacient);
    }
    @Test
    void obterTodosService(){//Teste basico para retornar todos os pacientes
       Assertions.assertEquals(pacientes , pacienteServiceImpl.obterTodos());
    }
    @Test
    void obterPorIdService(){//Teste basico para retornar o pacient por id do tipo String
        Assertions.assertEquals(pacient , pacienteServiceImpl.obterPorId(pacient.getId()));
    }

    @Test
    void inserirService(){//Verificar se inserção foi realizada com sucesso
        pacient.setId("56s56f5asfsdf565sdf");
        when(pacienteRepository.findAll()).thenReturn(pacientes);
        pacienteServiceImpl.inserir(pacient);
        Assertions.assertEquals(pacienteServiceImpl.obterPorId("56s56f5asfsdf565sdf"), pacient);
    }
    @Test
    void verificarCpfExistenteService(){
        Assertions.assertEquals(true,pacienteServiceImpl.verificarPaciente(pacient));
    }

}
