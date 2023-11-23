package com.vacinas.ap2;

import com.vacinas.ap2.entity.Endereco;
import com.vacinas.ap2.entity.Paciente;
import com.vacinas.ap2.enums.Estados;
import com.vacinas.ap2.enums.Sexo;
import com.vacinas.ap2.exceptions.CPFException;
import com.vacinas.ap2.exceptions.DateFormatException;
import com.vacinas.ap2.exceptions.PacientNotFoundException;
import com.vacinas.ap2.repository.PacienteRepository;
import com.vacinas.ap2.service.PacienteServiceImpl;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collector;

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
        pacient.setCpf("807.457.620-50");
        pacient.setDataNascimento("1991-01-21");
        pacient.setSexo(Sexo.feminino.toString());
        pacient.setContato("(74)99485365");
        pacient.setEndereco( new Endereco("av. 7 de setembro",24,"2 de julho","salvador", Estados.BA.toString()));
        pacientes.add(pacient);
        when(pacienteRepository.findAll()).thenReturn(pacientes);
    }
    @Test
    void obterTodosSucessoService(){//Teste basico para retornar todos os pacientes
       Assertions.assertEquals(pacientes , pacienteServiceImpl.obterTodos());
    }
    @Test
    void obterPorIdService(){//Teste basico para retornar o pacient por id do tipo String
        Assertions.assertEquals(pacient , pacienteServiceImpl.obterPorId("f5as6das6d65as6d61"));
    }
    @Test
    void obterPorIdErrorService(){//Teste basico para retornar o pacient por id do tipo String

        Assertions.assertThrows(PacientNotFoundException.class ,()-> pacienteServiceImpl.obterPorId("naoseraencontrado"));
    }

    @Test
    void inserirSucessoService(){//Verificar se inserção foi realizada com sucesso
        pacient.setId("f5as6das6d65as6d626");
        pacient.setCpf("575.127.020-78");
        pacienteRepository.insert(pacient);
        Assertions.assertEquals(pacienteServiceImpl.obterPorId(pacient.getId()), pacient);
    }
    @Test
    void verificarCpfExistenteSucessoService(){
        Assertions.assertEquals(true,pacienteServiceImpl.cpfVerificador(pacient));
    }

    @Test
    void insertCpfExistenteErrorService(){//Retorna uma exceção personalizada em caso do usuario cadastrar um cpf ja existente
        // Atribui que o usuario ja existe no banco de dados e ser usado no serviço do inserir paciente
        Assertions.assertThrows(CPFException.class, () -> pacienteServiceImpl.inserir(pacient));//Nesse momento de inserção o usuario já existe
    }

    @Test
    void pacienteTodosSucessoService(){//Compara o primeiro paciente com o outro e inclui informações caso ele esteja vazio.
        Paciente newPacient = new Paciente();
        newPacient.setNome(null);
        newPacient.setSobrenome(null);
        newPacient.setCpf("807.457.620-50");

        Paciente transformPaciente = pacienteServiceImpl.verificarPacienteTodos(pacient,newPacient);

        Assertions.assertEquals(pacient.getNome(), transformPaciente.getNome() );
        Assertions.assertEquals(pacient.getSobrenome(), transformPaciente.getSobrenome());
    }
    @Test
    void pacienteCpfErrorService(){
        Paciente newPacient = new Paciente();
        newPacient.setNome(null);
        newPacient.setSobrenome(null);
        newPacient.setCpf("80745762050");
        Assertions.assertThrows(CPFException.class,() ->pacienteServiceImpl.verificarPacienteTodos(newPacient,pacient));
    }
    @Test
    void pacienteDateErrorService(){
        Paciente newPacient = new Paciente();
        newPacient.setNome(null);
        newPacient.setSobrenome(null);
        newPacient.setCpf("807.457.620-50");
        newPacient.setDataNascimento("2999-07-25");
        Assertions.assertThrows(DateFormatException.class,() ->pacienteServiceImpl.verificarPacienteTodos(newPacient,pacient));

    }

    @Test
    void obterTodosError(){//Exibir exceção com lista vazia
        List<Paciente> listaVazia = new ArrayList<>();
        when(pacienteRepository.findAll()).thenReturn(listaVazia);
            Assertions.assertEquals(listaVazia,pacienteServiceImpl.obterTodos());
    }
    @Test
    void removerPorIdErrorService(){
        Assertions.assertThrows(PacientNotFoundException.class,() -> pacienteServiceImpl.deletePorId("IdIncorreto"));
    }
    @Test
    void removerPorIdNuloErrorService(){
        Assertions.assertThrows(PacientNotFoundException.class,() -> pacienteServiceImpl.deletePorId(null));
    }

}
