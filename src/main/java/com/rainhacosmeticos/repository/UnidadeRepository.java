package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnidadeRepository extends JpaRepository<Unidade, UUID> {
}
