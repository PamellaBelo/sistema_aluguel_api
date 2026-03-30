package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.UnidadeRequest;
import com.pamella.sistema_aluguel_api.dto.UnidadeResponse;
import com.pamella.sistema_aluguel_api.model.Imovel;
import com.pamella.sistema_aluguel_api.model.StatusUnidade;
import com.pamella.sistema_aluguel_api.model.Unidade;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import com.pamella.sistema_aluguel_api.repository.ImovelRepository;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final ImovelRepository imovelRepository;
    private final ContratoRepository contratoRepository;

    public UnidadeResponse criar(UnidadeRequest request) {
        Imovel imovel = imovelRepository.findById(request.imovelId())
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado."));

        Unidade unidade = Unidade.builder()
                .nome(request.nome())
                .imovel(imovel)
                .status(request.status() != null ? request.status() : StatusUnidade.VAGA)
                .build();

        return toResponse(unidadeRepository.save(unidade));
    }

    public List<UnidadeResponse> listar() {
        return unidadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<UnidadeResponse> listarPorImovel(Long imovelId) {
        return unidadeRepository.findByImovelId(imovelId).stream().map(this::toResponse).toList();
    }

    public List<UnidadeResponse> listarPorStatus(StatusUnidade status) {
        return unidadeRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    public UnidadeResponse atualizar(Long id, UnidadeRequest request) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada."));
        Imovel imovel = imovelRepository.findById(request.imovelId())
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado."));
        unidade.setNome(request.nome());
        unidade.setImovel(imovel);
        if (request.status() != null) unidade.setStatus(request.status());
        return toResponse(unidadeRepository.save(unidade));
    }

    public void deletar(Long id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada."));
        if (contratoRepository.existsByUnidadeIdAndAtivoTrue(id)) {
            throw new IllegalStateException("Esta unidade possui contrato ativo e não pode ser excluída.");
        }
        unidadeRepository.delete(unidade);
    }

    private UnidadeResponse toResponse(Unidade u) {
        return new UnidadeResponse(
                u.getId(),
                u.getNome(),
                u.getImovel().getId(),
                u.getImovel().getNome(),
                u.getStatus()
        );
    }
}