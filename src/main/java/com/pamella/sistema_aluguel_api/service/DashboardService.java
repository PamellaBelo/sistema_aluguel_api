package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.DashboardResponse;
import com.pamella.sistema_aluguel_api.model.StatusUnidade;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ImovelRepository imovelRepository;
    private final UnidadeRepository unidadeRepository;
    private final ContratoRepository contratoRepository;
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardResponse getDashboard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        long totalImoveis = imovelRepository.countByUsuarioId(usuario.getId());

        long totalUnidades = imovelRepository.findByUsuarioId(usuario.getId())
                .stream()
                .mapToLong(imovel -> unidadeRepository.countByImovelId(imovel.getId()))
                .sum();

        long unidadesAlugadas = unidadeRepository.countByStatus(StatusUnidade.ALUGADA);
        long unidadesVagas    = unidadeRepository.countByStatus(StatusUnidade.VAGA);
        var  valorTotal       = contratoRepository.somarValorAluguelAtivos();
        long contasVencidas   = contaRepository.countContasVencidas(LocalDate.now());

        return new DashboardResponse(
                totalImoveis,
                totalUnidades,
                unidadesAlugadas,
                unidadesVagas,
                valorTotal,
                contasVencidas
        );
    }
}