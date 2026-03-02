package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    private BigDecimal valor;

    private LocalDate vencimento;

    @ManyToOne
    @JoinColumn(name = "casa_id")
    private Casa casa;
}
