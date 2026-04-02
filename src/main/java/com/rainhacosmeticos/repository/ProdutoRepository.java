package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    Optional<Produto> findByEan(String ean);
}
