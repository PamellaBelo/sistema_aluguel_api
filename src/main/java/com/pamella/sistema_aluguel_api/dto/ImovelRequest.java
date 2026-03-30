package com.pamella.sistema_aluguel_api.dto;

import jakarta.validation.constraints.NotBlank;

public record ImovelRequest(
        @NotBlank String nome,
        String endereco
) {
}
