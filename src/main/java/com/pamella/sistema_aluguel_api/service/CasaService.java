package com.pamella.sistema_aluguel_api.service;


import com.pamella.sistema_aluguel_api.dto.CasaRequest;
import com.pamella.sistema_aluguel_api.dto.CasaResponse;
import com.pamella.sistema_aluguel_api.model.Casa;
import com.pamella.sistema_aluguel_api.model.Unidade;
import com.pamella.sistema_aluguel_api.repository.CasaRepository;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CasaService {

    private final CasaRepository casaRepository;
    private final UnidadeRepository unidadeRepository;

    public CasaResponse criar(CasaRequest request) {

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        Casa casa = Casa.builder()
                .numero(request.numero())
                .unidade(unidade)
                .build();

        casaRepository.save(casa);

        return new CasaResponse(casa.getId(), casa.getNumero(), unidade.getId());
    }

    public List<CasaResponse> listarPorUnidade(Long unidadeId) {
        return casaRepository.findByUnidadeId(unidadeId)
                .stream()
                .map(c -> new CasaResponse(c.getId(), c.getNumero(), c.getUnidade().getId()))
                .toList();
    }

    public List<CasaResponse> listarTodas() {
        return casaRepository.findAll()
                .stream()
                .map(c -> new CasaResponse(c.getId(), c.getNumero(), c.getUnidade().getId()))
                .toList();
    }
}