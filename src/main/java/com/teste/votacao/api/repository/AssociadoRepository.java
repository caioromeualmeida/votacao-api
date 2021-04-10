package com.teste.votacao.api.repository;

import java.util.Optional;

import com.teste.votacao.api.model.Associado;

import org.springframework.data.repository.CrudRepository;

//como só foi usado o método save, foi extendido pela classe crud
public interface AssociadoRepository extends CrudRepository<Associado, Long> {
    Optional<Associado> findAssociadoByCpf(String cpf);
}