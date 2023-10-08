package com.vacinas.ap2.repository;

import com.vacinas.ap2.entity.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnderecoRepository extends MongoRepository<Endereco,String> {

}
