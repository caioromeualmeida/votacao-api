package com.teste.votacao.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String descricao;

    private int tempoSessao;

    private Date prazoVotacao;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "votoId.pautaId", fetch = FetchType.EAGER)
    private List<Voto> votos;
}
