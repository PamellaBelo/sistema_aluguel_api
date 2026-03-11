package com.pamella.sistema_aluguel_api.service;
import com.pamella.sistema_aluguel_api.dto.UnidadeRequest;
import com.pamella.sistema_aluguel_api.dto.UnidadeResponse;
import com.pamella.sistema_aluguel_api.model.Unidade;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import com.pamella.sistema_aluguel_api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;
    private final UsuarioRepository usuarioRepository;

    public UnidadeResponse criar(UnidadeRequest request) {
        Usuario usuario = getUsuarioLogado();

        Unidade unidade = Unidade.builder()
                .nome(request.nome())
                .usuario(usuario)
                .build();

        repository.save(unidade);
        return toResponse(unidade);
    }

    public List<UnidadeResponse> listar() {
        Usuario usuario = getUsuarioLogado();
        return repository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deletar(Long id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        repository.delete(unidade);
    }

    private Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private UnidadeResponse toResponse(Unidade u) {
        return new UnidadeResponse(u.getId(), u.getNome());
    }
}