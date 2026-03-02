package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.UnidadeRequest;
import com.pamella.sistema_aluguel_api.dto.UnidadeResponse;
import com.pamella.sistema_aluguel_api.model.Unidade;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import com.pamella.sistema_aluguel_api.repository.UsuarioRepository;
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
        // pega o email do usuário logado via JWT
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Unidade unidade = Unidade.builder()
                .nome(request.nome())
                .usuario(usuario)
                .build();

        repository.save(unidade);

        return new UnidadeResponse(unidade.getId(), unidade.getNome());
    }

    public List<UnidadeResponse> listar() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findByUsuarioId(usuario.getId())
                .stream()
                .map(u -> new UnidadeResponse(u.getId(), u.getNome()))
                .toList();
    }
}
