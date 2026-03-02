package com.pamella.sistema_aluguel_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "casas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
}
