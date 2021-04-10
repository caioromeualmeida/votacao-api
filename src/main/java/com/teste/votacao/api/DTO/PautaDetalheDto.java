package com.teste.votacao.api.DTO;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PautaDetalheDto {
    private Long id;
    private String descricao;
    private int tempoSessao;
    private Date prazoVotacao;
    private List<VotoDetalheDto> votos;
    private String resultado;
    private Long votosSim;
    private Long votosNao;
}