package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "casas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    // ✅ Enum em vez de String livre — evita valores inválidos no banco
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusCasa status = StatusCasa.VAGA;
}