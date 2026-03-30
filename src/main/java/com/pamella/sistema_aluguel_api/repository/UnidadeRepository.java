package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.StatusUnidade;
import com.pamella.sistema_aluguel_api.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    List<Unidade> findByImovelId(Long imovelId);
    long countByImovelId(Long imovelId);
    List<Unidade> findByStatus(StatusUnidade status);
    long countByStatus(StatusUnidade status);
    boolean existsByImovelId(Long imovelId);
}
