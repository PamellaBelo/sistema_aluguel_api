package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.StatusUnidade;

public record UnidadeResponse(Long id, String nome, Long imovelId, String nomeImovel, StatusUnidade status) {}
