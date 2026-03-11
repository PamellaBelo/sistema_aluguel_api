package com.pamella.sistema_aluguel_api.dto;

import jakarta.validation.constraints.NotBlank;

public record InquilinoRequest(
        @NotBlank String nome,
        String cpf,
        String telefone,
        String email
) {}
