package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.dto.ImovelRequest;
import com.pamella.sistema_aluguel_api.dto.ImovelResponse;
import com.pamella.sistema_aluguel_api.model.Imovel;
import com.pamella.sistema_aluguel_api.model.Usuario;
import com.pamella.sistema_aluguel_api.repository.ImovelRepository;
import com.pamella.sistema_aluguel_api.repository.UnidadeRepository;
import com.pamella.sistema_aluguel_api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final UnidadeRepository unidadeRepository;
    private final UsuarioRepository usuarioRepository;

    private Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    public ImovelResponse criar(ImovelRequest request) {
        Usuario usuario = getUsuarioLogado();
        Imovel imovel = Imovel.builder()
                .nome(request.nome())
                .endereco(request.endereco())
                .usuario(usuario)
                .build();
        imovel = imovelRepository.save(imovel);
        return toResponse(imovel);
    }

    public List<ImovelResponse> listar() {
        Usuario usuario = getUsuarioLogado();
        return imovelRepository.findByUsuarioId(usuario.getId())
                .stream().map(this::toResponse).toList();
    }

    public void deletar(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado."));
        long unidades = unidadeRepository.countByImovelId(id);
        if (unidades > 0) {
            throw new IllegalStateException("Este imóvel possui unidades cadastradas e não pode ser excluído.");
        }
        imovelRepository.delete(imovel);
    }

    private ImovelResponse toResponse(Imovel i) {
        return new ImovelResponse(
                i.getId(),
                i.getNome(),
                i.getEndereco(),
                unidadeRepository.countByImovelId(i.getId())
        );
    }
    public ImovelResponse atualizar(Long id, ImovelRequest request) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado."));
        imovel.setNome(request.nome());
        imovel.setEndereco(request.endereco());
        imovel = imovelRepository.save(imovel);
        return toResponse(imovel);
    }
}
