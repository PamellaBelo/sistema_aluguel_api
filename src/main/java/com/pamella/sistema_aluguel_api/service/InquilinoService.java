package com.pamella.sistema_aluguel_api.service;


import com.pamella.sistema_aluguel_api.dto.InquilinoRequest;
import com.pamella.sistema_aluguel_api.dto.InquilinoResponse;
import com.pamella.sistema_aluguel_api.model.Inquilino;
import com.pamella.sistema_aluguel_api.repository.ContratoRepository;
import com.pamella.sistema_aluguel_api.repository.InquilinoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquilinoService {

    private final InquilinoRepository inquilinoRepository;
    private final ContratoRepository contratoRepository;

    public List<InquilinoResponse> listar() {
        return inquilinoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public InquilinoResponse buscarPorId(Long id) {
        return toResponse(buscar(id));
    }

    public InquilinoResponse criar(InquilinoRequest request) {
        Inquilino inquilino = Inquilino.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .telefone(request.telefone())
                .email(request.email())
                .build();
        inquilinoRepository.save(inquilino);
        return toResponse(inquilino);
    }

    public InquilinoResponse atualizar(Long id, InquilinoRequest request) {
        Inquilino inquilino = buscar(id);
        inquilino.setNome(request.nome());
        inquilino.setCpf(request.cpf());
        inquilino.setTelefone(request.telefone());
        inquilino.setEmail(request.email());
        inquilinoRepository.save(inquilino);
        return toResponse(inquilino);
    }

    public void deletar(Long id) {
        buscar(id);
        if (contratoRepository.existsByInquilinoIdAndAtivoTrue(id)) {
            throw new IllegalStateException("Inquilino possui contrato ativo e não pode ser excluído");
        }
        inquilinoRepository.deleteById(id);
    }

    private Inquilino buscar(Long id) {
        return inquilinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inquilino não encontrado"));
    }

    private InquilinoResponse toResponse(Inquilino i) {
        return new InquilinoResponse(i.getId(), i.getNome(), i.getCpf(), i.getTelefone(), i.getEmail());
    }
}