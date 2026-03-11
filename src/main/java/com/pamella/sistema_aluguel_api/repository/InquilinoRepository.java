package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Inquilino;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
}
