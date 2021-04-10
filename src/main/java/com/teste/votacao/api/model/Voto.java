package com.teste.votacao.api.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data
@Entity
public class Voto {
    @EmbeddedId
    private VotoId votoId;

    private Boolean voto;

    @MapsId("associadoId")
    @ManyToOne(optional = true)
    @JoinColumn(name = "associadoId", referencedColumnName = "id", insertable = true, updatable = true)
    private Associado associado;
}
