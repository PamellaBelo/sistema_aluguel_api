package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pagamentos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    // ✅ Enum tipado em vez de String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    // ✅ Referência ao mês de referência (ex: 2025-06 = aluguel de junho)
    private LocalDate mesReferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
}
