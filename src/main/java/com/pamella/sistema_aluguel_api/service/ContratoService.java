package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.ContratoRequest;
import com.pamella.sistema_aluguel_api.dto.ContratoResponse;
import com.pamella.sistema_aluguel_api.model.*;
import com.pamella.sistema_aluguel_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final UnidadeRepository unidadeRepository;
    private final InquilinoRepository inquilinoRepository;
    private final ContaRepository contaRepository;

    public ContratoResponse criar(ContratoRequest request) {
        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada."));
        Inquilino inquilino = inquilinoRepository.findById(request.inquilinoId())
                .orElseThrow(() -> new EntityNotFoundException("Inquilino não encontrado."));

        if (contratoRepository.existsByUnidadeIdAndAtivoTrue(request.unidadeId())) {
            throw new IllegalStateException("Esta unidade já possui um contrato ativo.");
        }
        if (contratoRepository.existsByInquilinoIdAndAtivoTrue(request.inquilinoId())) {
            throw new IllegalStateException("Este inquilino já possui um contrato ativo.");
        }

        Contrato contrato = Contrato.builder()
                .unidade(unidade)
                .inquilino(inquilino)
                .valorAluguel(request.valorAluguel())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .ativo(true)
                .build();

        unidade.setStatus(StatusUnidade.ALUGADA);
        unidadeRepository.save(unidade);

        return toResponse(contratoRepository.save(contrato));
    }

    public List<ContratoResponse> listar() {
        return contratoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ContratoResponse buscarPorId(Long id) {
        return toResponse(contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado.")));
    }

    public ContratoResponse encerrar(Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado."));
        if (!contrato.isAtivo()) {
            throw new IllegalStateException("Este contrato já está encerrado.");
        }
        contrato.setAtivo(false);
        contrato.getUnidade().setStatus(StatusUnidade.VAGA);
        unidadeRepository.save(contrato.getUnidade());
        return toResponse(contratoRepository.save(contrato));
    }

    public BigDecimal calcularTotalMensal(Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado."));
        BigDecimal totalContas = contaRepository.findByContratoId(id)
                .stream()
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return contrato.getValorAluguel().add(totalContas);
    }

    private ContratoResponse toResponse(Contrato c) {
        return new ContratoResponse(
                c.getId(),
                c.getUnidade().getId(),
                c.getUnidade().getNome(),
                c.getUnidade().getImovel().getNome(),
                c.getInquilino().getId(),
                c.getInquilino().getNome(),
                c.getValorAluguel(),
                c.getDataInicio(),
                c.getDataFim(),
                c.isAtivo()
        );
    }
}