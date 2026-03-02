package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.model.Conta;
import com.pamella.sistema_aluguel_api.model.Pagamento;
import com.pamella.sistema_aluguel_api.model.StatusPagamento;
import com.pamella.sistema_aluguel_api.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ContaService contaService;

    public Pagamento registrarPagamento(Pagamento pagamento) {

        pagamento.setStatus(StatusPagamento.PAGO.name());

        BigDecimal valorTotal = calcularValorTotalPagamento(
                pagamento.getContrato().getId()
        );

        pagamento.setValorPago(valorTotal);

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    public List<Pagamento> buscarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

    // 🔥 AGORA RETORNA BIGDECIMAL
    public BigDecimal calcularValorTotalPagamento(Long contratoId) {

        List<Conta> contas = contaService.buscarContasPorContrato(contratoId);

        BigDecimal total = BigDecimal.ZERO;

        for (Conta conta : contas) {
            total = total.add(conta.getValor()); // assumindo que Conta.valor é BigDecimal
        }

        return total;
    }
}