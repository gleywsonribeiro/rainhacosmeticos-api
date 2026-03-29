package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.NotaDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotaDeCompraRepository extends JpaRepository<NotaDeCompra, UUID> {

    boolean existsByFornecedor_Id(UUID fornecedorId);
}
