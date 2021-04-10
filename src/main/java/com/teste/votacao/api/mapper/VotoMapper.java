package com.teste.votacao.api.mapper;

import java.util.List;

import com.teste.votacao.api.DTO.VotoDetalheDto;
import com.teste.votacao.api.DTO.VotoDto;
import com.teste.votacao.api.model.Voto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface VotoMapper {
    VotoMapper INSTANCE = Mappers.getMapper( VotoMapper.class );
    
    VotoDto toDto(Voto voto);
    Voto toModel(VotoDto votoDto);

    @Mapping(source = "votoId.pautaId", target = "pautaId")
    @Mapping(source = "votoId.associadoId", target = "associadoId")
    @Mapping(source = "associado.cpf", target = "cpf")
    VotoDetalheDto toDetalheDto(Voto voto);
    List<VotoDetalheDto> toDetalhesDto(List<Voto> votos);
}
