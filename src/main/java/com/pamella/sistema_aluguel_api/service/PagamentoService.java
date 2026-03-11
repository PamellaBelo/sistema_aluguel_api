package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.PagamentoRequest;
import com.pamella.sistema_aluguel_api.dto.PagamentoResponse;
import com.pamella.sistema_aluguel_api.model.Conta;
import com.pamella.sistema_aluguel_api.model.Contrato;
import com.pamella.sistema_aluguel_api.model.Pagamento;
import com.pamella.sistema_aluguel_api.model.StatusPagamento;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import com.pamella.sistema_aluguel_api.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ContratoRepository contratoRepository;
    private final ContaService contaService;

    public PagamentoResponse registrar(PagamentoRequest request) {
        Contrato contrato = contratoRepository.findById(request.contratoId())
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado"));

        if (!contrato.isAtivo()) {
            throw new IllegalStateException("Não é possível registrar pagamento em contrato encerrado");
        }

        pagamentoRepository.findByContratoIdAndMesReferencia(
                request.contratoId(), request.mesReferencia()
        ).ifPresent(p -> {
            throw new IllegalStateException("Pagamento já registrado para este mês");
        });

        BigDecimal totalContas = contaService.buscarContasPorContrato(contrato.getId())
                .stream()
                .map(Conta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotal = contrato.getValorAluguel().add(totalContas);

        LocalDate vencimentoEsperado = request.mesReferencia().withDayOfMonth(10);
        StatusPagamento status = request.dataPagamento().isAfter(vencimentoEsperado)
                ? StatusPagamento.ATRASADO
                : StatusPagamento.PAGO;

        Pagamento pagamento = Pagamento.builder()
                .contrato(contrato)
                .dataPagamento(request.dataPagamento())
                .mesReferencia(request.mesReferencia())
                .valorPago(valorTotal)
                .status(status)
                .build();

        pagamentoRepository.save(pagamento);
        return toResponse(pagamento);
    }

    public List<PagamentoResponse> listarTodos() {
        return pagamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<PagamentoResponse> listarPorContrato(Long contratoId) {
        return pagamentoRepository.findByContratoId(contratoId)
                .stream().map(this::toResponse).toList();
    }

    public PagamentoResponse buscarPorId(Long id) {
        return toResponse(buscar(id));
    }

    public void deletar(Long id) {
        buscar(id);
        pagamentoRepository.deleteById(id);
    }

    private Pagamento buscar(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
    }

    private PagamentoResponse toResponse(Pagamento p) {
        return new PagamentoResponse(
                p.getId(),
                p.getContrato().getId(),
                p.getContrato().getInquilino().getNome(),
                p.getContrato().getCasa().getNumero(),
                p.getValorPago(),
                p.getDataPagamento(),
                p.getMesReferencia(),
                p.getStatus()
        );
    }
}