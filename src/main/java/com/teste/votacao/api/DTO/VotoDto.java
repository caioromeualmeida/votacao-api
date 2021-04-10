package com.teste.votacao.api.DTO;

import lombok.Data;

@Data
public class VotoDto { 
    private String cpf;
    private Boolean voto;
}