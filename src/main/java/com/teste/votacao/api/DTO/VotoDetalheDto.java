package com.teste.votacao.api.DTO;

import lombok.Data;

@Data
public class VotoDetalheDto { 
    private Long pautaId;
    private Long associadoId;
    private String cpf;
    private Boolean voto;
}