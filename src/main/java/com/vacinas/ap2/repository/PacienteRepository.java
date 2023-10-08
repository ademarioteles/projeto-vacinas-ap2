package com.vacinas.ap2.repository;

import com.vacinas.ap2.entity.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente,String> {

}
