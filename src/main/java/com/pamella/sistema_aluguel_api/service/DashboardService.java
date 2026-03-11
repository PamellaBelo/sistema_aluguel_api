package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.DashboardResponse;
import com.pamella.sistema_aluguel_api.model.StatusCasa;
import com.pamella.sistema_aluguel_api.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UnidadeRepository unidadeRepository;
    private final CasaRepository casaRepository;
    private final ContratoRepository contratoRepository;
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardResponse obterDadosDashboard() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long usuarioId = usuarioRepository.findByEmail(email)
                .orElseThrow().getId();

        long totalUnidades = unidadeRepository.countByUsuarioId(usuarioId);

        long casasAlugadas = casaRepository.countByStatus(StatusCasa.ALUGADA);
        long casasVagas = casaRepository.countByStatus(StatusCasa.VAGA);

        BigDecimal valorTotalReceber = contratoRepository.somarValorAluguelAtivos();

        long contasPendentes = contaRepository.countContasVencidas(LocalDate.now());

        return new DashboardResponse(
                totalUnidades,
                casasAlugadas,
                casasVagas,
                valorTotalReceber,
                contasPendentes
        );
    }
}