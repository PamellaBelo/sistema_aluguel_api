package com.pamella.sistema_aluguel_api.dto;

import jakarta.validation.constraints.NotBlank;

public record UnidadeRequest(@NotBlank String nome) {}
