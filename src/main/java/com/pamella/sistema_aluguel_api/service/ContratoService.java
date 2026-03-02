package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.model.Conta;
import com.pamella.sistema_aluguel_api.model.Contrato;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContaService contaService;

    public BigDecimal calcularTotalMensal(Long contratoId) {

        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));


        BigDecimal total = BigDecimal.valueOf(contrato.getValorAluguel());

        List<Conta> contas = contaService
                .buscarContasPorCasa(contrato.getCasa().getId());

        for (Conta conta : contas) {
            total = total.add(conta.getValor());
        }

        return total;
    }
}
