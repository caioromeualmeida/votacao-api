package com.teste.votacao.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class VotoId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long pautaId;
    private Long associadoId;
}
