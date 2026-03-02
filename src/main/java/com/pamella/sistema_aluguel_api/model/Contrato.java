package com.pamella.sistema_aluguel_api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valorAluguel;
    private Date dataInicio;
    private Date dataFim;

    @ManyToOne
    @JoinColumn(name = "casa_id")
    private Casa casa;

    @ManyToOne
    @JoinColumn(name = "inquilino_id")
    private Inquilino inquilino;
}
