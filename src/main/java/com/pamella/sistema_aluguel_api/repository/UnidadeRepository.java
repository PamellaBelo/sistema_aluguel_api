package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    List<Unidade> findByUsuarioId(Long usuarioId);
    long countByUsuarioId(Long usuarioId);
}
