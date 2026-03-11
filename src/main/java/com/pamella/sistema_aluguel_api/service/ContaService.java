package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.ContaRequest;
import com.pamella.sistema_aluguel_api.dto.ContaResponse;
import com.pamella.sistema_aluguel_api.model.Conta;
import com.pamella.sistema_aluguel_api.model.Contrato;
import com.pamella.sistema_aluguel_api.repository.ContaRepository;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ContratoRepository contratoRepository;

    public ContaResponse criar(ContaRequest request) {
        Contrato contrato = contratoRepository.findById(request.contratoId())
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado"));

        if (!contrato.isAtivo()) {
            throw new IllegalStateException("Não é possível adicionar contas a um contrato encerrado");
        }

        Conta conta = Conta.builder()
                .contrato(contrato)
                .tipo(request.tipo())
                .valor(request.valor())
                .vencimento(request.vencimento())
                .build();

        contaRepository.save(conta);
        return toResponse(conta);
    }

    public List<ContaResponse> listarPorContrato(Long contratoId) {
        return contaRepository.findByContratoId(contratoId)
                .stream().map(this::toResponse).toList();
    }

    public ContaResponse buscarPorId(Long id) {
        return toResponse(buscar(id));
    }

    public void deletar(Long id) {
        buscar(id);
        contaRepository.deleteById(id);
    }

    // ✅ Usado internamente pelo PagamentoService e ContratoService
    public List<Conta> buscarContasPorContrato(Long contratoId) {
        return contaRepository.findByContratoId(contratoId);
    }

    private Conta buscar(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }

    private ContaResponse toResponse(Conta c) {
        return new ContaResponse(
                c.getId(),
                c.getContrato().getId(),
                c.getTipo(),
                c.getValor(),
                c.getVencimento()
        );
    }
}
