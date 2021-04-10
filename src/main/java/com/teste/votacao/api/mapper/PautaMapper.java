package com.teste.votacao.api.mapper;

import java.util.List;

import com.teste.votacao.api.DTO.PautaDetalheDto;
import com.teste.votacao.api.DTO.PautaDto;
import com.teste.votacao.api.model.Pauta;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", uses = VotoMapper.class)
public interface PautaMapper {
    PautaMapper INSTANCE = Mappers.getMapper( PautaMapper.class );
    PautaDto toDto(Pauta pauta);
    Pauta toModel(PautaDto pautaDto);

    PautaDetalheDto toDetalheDto(Pauta pauta);
    List<PautaDetalheDto> toDetalhesDto(List<Pauta> pauta);
}
