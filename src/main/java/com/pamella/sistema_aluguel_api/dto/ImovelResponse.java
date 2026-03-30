package com.pamella.sistema_aluguel_api.dto;

public record ImovelResponse(
        Long id,
        String nome,
        String endereco,
        long totalUnidades
) {
}
