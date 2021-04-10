package com.teste.votacao.api.DTO;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PautaDto {
    @Size(min = 3, max = 255, message = "Tamanho permitido entre: 3 e 255 caracteres.")
    @NotNull(message = "Descrição da pauta não pode ser nula.")
    private String descricao;

    @Max(value = 60, message = "Tempo máximo deve ser de 60 minutos.")
    private int tempoSessao;

    private Date prazoVotacao;
}