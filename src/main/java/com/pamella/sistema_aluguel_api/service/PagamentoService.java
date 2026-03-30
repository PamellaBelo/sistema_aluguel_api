package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.PagamentoRequest;
import com.pamella.sistema_aluguel_api.dto.PagamentoResponse;
import com.pamella.sistema_aluguel_api.model.*;
import com.pamella.sistema_aluguel_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ContratoRepository contratoRepository;
    private final ContaRepository contaRepository;

    public PagamentoResponse registrar(PagamentoRequest request) {
        Contrato contrato = contratoRepository.findById(request.contratoId())
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado."));

        if (!contrato.isAtivo()) {
            throw new IllegalStateException("Este contrato não está ativo.");
        }

        boolean duplicado = pagamentoRepository.findByContratoIdAndMesReferencia(
                request.contratoId(), request.mesReferencia()).isPresent();
        if (duplicado) {
            throw new IllegalStateException("Já existe um pagamento registrado para este mês.");
        }

        BigDecimal totalContas = contaRepository.findByContratoId(request.contratoId())
                .stream().map(c -> c.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorTotal = contrato.getValorAluguel().add(totalContas);

        StatusPagamento status = request.dataPagamento().getDayOfMonth() <= 10
                ? StatusPagamento.PAGO
                : StatusPagamento.ATRASADO;

        Pagamento pagamento = Pagamento.builder()
                .contrato(contrato)
                .dataPagamento(request.dataPagamento())
                .mesReferencia(request.mesReferencia())
                .valorPago(valorTotal)
                .status(status)
                .build();

        return toResponse(pagamentoRepository.save(pagamento));
    }

    public List<PagamentoResponse> listarTodos() {
        return pagamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<PagamentoResponse> listarPorContrato(Long contratoId) {
        return pagamentoRepository.findByContratoId(contratoId).stream().map(this::toResponse).toList();
    }

    public PagamentoResponse buscarPorId(Long id) {
        return toResponse(pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado.")));
    }

    public void deletar(Long id) {
        Pagamento p = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado."));
        pagamentoRepository.delete(p);
    }

    private PagamentoResponse toResponse(Pagamento p) {
        return new PagamentoResponse(
                p.getId(),
                p.getContrato().getId(),
                p.getContrato().getInquilino().getNome(),
                p.getContrato().getUnidade().getNome(),
                p.getContrato().getUnidade().getImovel().getNome(),
                p.getValorPago(),
                p.getDataPagamento(),
                p.getMesReferencia(),
                p.getStatus()
        );
    }
}