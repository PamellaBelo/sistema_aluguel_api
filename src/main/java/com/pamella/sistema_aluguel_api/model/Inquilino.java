package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inquilinos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquilino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // ✅ Campos extras úteis para um sistema de aluguel
    private String cpf;
    private String telefone;
    private String email;
}

