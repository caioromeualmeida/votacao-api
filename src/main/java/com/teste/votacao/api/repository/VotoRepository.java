package com.teste.votacao.api.repository;

import java.util.List;

import com.teste.votacao.api.model.Voto;
import com.teste.votacao.api.model.VotoId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, VotoId> {
    List<Voto> findByVotoIdPautaId(Long pautaId);
}