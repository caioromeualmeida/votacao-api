package com.teste.votacao.api.mapper;

import com.teste.votacao.api.DTO.AssociadoDto;
import com.teste.votacao.api.model.Associado;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface AssociadoMapper {
    AssociadoMapper INSTANCE = Mappers.getMapper( AssociadoMapper.class );
    AssociadoDto toDto(Associado associado);
    Associado toModel(AssociadoDto associadoDto);
}
