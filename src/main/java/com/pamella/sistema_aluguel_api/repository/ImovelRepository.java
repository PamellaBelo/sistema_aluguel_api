package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    List<Imovel> findByUsuarioId(Long usuarioId);
    long countByUsuarioId(Long usuarioId);
}