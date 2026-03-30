package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imoveis")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String endereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}

