package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @OneToMany(mappedBy = "aluguel", cascade = CascadeType.ALL)
    private List<Conta> contas;

    @Column(nullable = false)
    private BigDecimal valorAluguel;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private boolean pago;

    public Aluguel(){}
}
