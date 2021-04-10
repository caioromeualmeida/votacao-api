package com.teste.votacao.api.service;

import java.util.List;

import com.teste.votacao.api.DTO.PautaDetalheDto;
import com.teste.votacao.api.DTO.PautaDto;
import com.teste.votacao.api.DTO.VotoDetalheDto;
import com.teste.votacao.api.DTO.VotoDto;

public interface PautaService {
    PautaDetalheDto novaPauta(PautaDto pauta);
    VotoDetalheDto novoVoto(Long pautaId, VotoDto votoDto);
    PautaDetalheDto getPauta(Long pautaId);
    List<PautaDetalheDto> getPautas();
    List<VotoDetalheDto> getVotos(Long pautaId);
}