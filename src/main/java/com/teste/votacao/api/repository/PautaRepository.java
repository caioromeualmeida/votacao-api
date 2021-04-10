package com.teste.votacao.api.repository;

import com.teste.votacao.api.model.Pauta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

}