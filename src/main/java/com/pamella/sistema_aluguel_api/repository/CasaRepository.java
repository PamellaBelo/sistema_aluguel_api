package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaRepository extends JpaRepository<Casa, Long> {
    List<Casa> findByUnidadeId(Long unidadeId); // listar casas de uma unidade
}
