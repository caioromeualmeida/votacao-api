package com.teste.votacao.api.service;

import com.teste.votacao.api.DTO.AssociadoDto;

public interface AssociadoService {
    AssociadoDto novoAssociado(AssociadoDto associado);
    Boolean permitidoVotar(String cpf);
}