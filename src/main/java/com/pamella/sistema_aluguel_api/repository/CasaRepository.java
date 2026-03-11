package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Casa;
import com.pamella.sistema_aluguel_api.model.StatusCasa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByUnidadeId(Long unidadeId);
    List<Casa> findByStatus(StatusCasa status);
    // ✅ Tipado com enum em vez de String
    long countByStatus(StatusCasa status);
}
