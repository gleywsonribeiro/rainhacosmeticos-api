package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {
}
