package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unidades")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
