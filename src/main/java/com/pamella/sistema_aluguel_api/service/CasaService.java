package com.pamella.sistema_aluguel_api.service;


import com.pamella.sistema_aluguel_api.dto.CasaRequest;
import com.pamella.sistema_aluguel_api.dto.CasaResponse;
import com.pamella.sistema_aluguel_api.model.Casa;
import com.pamella.sistema_aluguel_api.model.StatusCasa;
import com.pamella.sistema_aluguel_api.model.Unidade;
import com.pamella.sistema_aluguel_api.repository.CasaRepository;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CasaService {

    private final CasaRepository casaRepository;
    private final UnidadeRepository unidadeRepository;
    private final ContratoRepository contratoRepository;

    public CasaResponse criar(CasaRequest request) {
        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

        StatusCasa status = request.status() != null ? request.status() : StatusCasa.VAGA;

        Casa casa = Casa.builder()
                .numero(request.numero())
                .unidade(unidade)
                .status(status)
                .build();

        casaRepository.save(casa);
        return toResponse(casa);
    }

    public List<CasaResponse> listarPorUnidade(Long unidadeId) {
        return casaRepository.findByUnidadeId(unidadeId)
                .stream().map(this::toResponse).toList();
    }

    public List<CasaResponse> listarTodas() {
        return casaRepository.findAll()
                .stream().map(this::toResponse).toList();
    }

    public List<CasaResponse> listarPorStatus(StatusCasa status) {
        return casaRepository.findByStatus(status)
                .stream().map(this::toResponse).toList();
    }

    public void deletar(Long id) {
        Casa casa = casaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Casa não encontrada"));

        if (contratoRepository.existsByCasaIdAndAtivoTrue(id)) {
            throw new IllegalStateException("Casa possui contrato ativo e não pode ser excluída");
        }

        casaRepository.delete(casa);
    }

    private CasaResponse toResponse(Casa c) {
        return new CasaResponse(
                c.getId(),
                c.getNumero(),
                c.getUnidade().getId(),
                c.getUnidade().getNome(),
                c.getStatus()
        );
    }
}