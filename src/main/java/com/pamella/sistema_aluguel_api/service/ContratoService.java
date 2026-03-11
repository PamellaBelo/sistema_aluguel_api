package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.ContratoRequest;
import com.pamella.sistema_aluguel_api.dto.ContratoResponse;
import com.pamella.sistema_aluguel_api.model.*;
import com.pamella.sistema_aluguel_api.repository.CasaRepository;
import com.pamella.sistema_aluguel_api.repository.ContaRepository;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import com.pamella.sistema_aluguel_api.repository.InquilinoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final CasaRepository casaRepository;
    private final InquilinoRepository inquilinoRepository;
    private final ContaRepository contaRepository;

    @Transactional
    public ContratoResponse criar(ContratoRequest request) {
        // ✅ Regra de negócio: casa só pode ter 1 contrato ativo
        if (contratoRepository.existsByCasaIdAndAtivoTrue(request.casaId())) {
            throw new IllegalStateException("Esta casa já possui um contrato ativo");
        }

        Casa casa = casaRepository.findById(request.casaId())
                .orElseThrow(() -> new EntityNotFoundException("Casa não encontrada"));

        Inquilino inquilino = inquilinoRepository.findById(request.inquilinoId())
                .orElseThrow(() -> new EntityNotFoundException("Inquilino não encontrado"));

        Contrato contrato = Contrato.builder()
                .casa(casa)
                .inquilino(inquilino)
                .valorAluguel(request.valorAluguel())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .ativo(true)
                .build();

        contratoRepository.save(contrato);

        // ✅ Atualiza status da casa para ALUGADA automaticamente
        casa.setStatus(StatusCasa.ALUGADA);
        casaRepository.save(casa);

        return toResponse(contrato);
    }

    public List<ContratoResponse> listar() {
        return contratoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ContratoResponse buscarPorId(Long id) {
        return toResponse(buscar(id));
    }

    @Transactional
    public ContratoResponse encerrar(Long id) {
        Contrato contrato = buscar(id);

        if (!contrato.isAtivo()) {
            throw new IllegalStateException("Contrato já está encerrado");
        }

        contrato.setAtivo(false);
        contrato.setDataFim(LocalDate.now());
        contratoRepository.save(contrato);

        // ✅ Libera a casa ao encerrar o contrato
        Casa casa = contrato.getCasa();
        casa.setStatus(StatusCasa.VAGA);
        casaRepository.save(casa);

        return toResponse(contrato);
    }

    // ✅ Cálculo correto: aluguel + todas as contas do contrato
    public BigDecimal calcularTotalMensal(Long contratoId) {
        Contrato contrato = buscar(contratoId);

        BigDecimal totalContas = contaRepository.findByContratoId(contratoId)
                .stream()
                .map(Conta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return contrato.getValorAluguel().add(totalContas);
    }

    private Contrato buscar(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado"));
    }

    private ContratoResponse toResponse(Contrato c) {
        return new ContratoResponse(
                c.getId(),
                c.getCasa().getId(),
                c.getCasa().getNumero(),
                c.getInquilino().getId(),
                c.getInquilino().getNome(),
                c.getValorAluguel(),
                c.getDataInicio(),
                c.getDataFim(),
                c.isAtivo()
        );
    }
}