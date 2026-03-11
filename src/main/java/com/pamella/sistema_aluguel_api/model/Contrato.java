package com.pamella.sistema_aluguel_api.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "contratos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ BigDecimal em vez de Double para valores monetários (evita erros de arredondamento)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    // ✅ LocalDate em vez de Date (API moderna do Java)
    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    // ✅ Campo ativo: permite saber se o contrato está vigente sem depender de dataFim nula
    @Column(nullable = false)
    @Builder.Default
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casa_id", nullable = false)
    private Casa casa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquilino_id", nullable = false)
    private Inquilino inquilino;
}