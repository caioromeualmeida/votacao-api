package com.teste.votacao.api.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AssociadoDto {
    private Long id;

    @NotNull(message = "CPF não pode ser nulo.")
    @Size(min = 11, max=11, message = "Quantidade de dígitos inválida.")
    private String cpf;
}
