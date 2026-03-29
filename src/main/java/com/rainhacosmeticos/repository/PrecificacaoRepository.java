package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Precificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrecificacaoRepository extends JpaRepository<Precificacao, UUID> {

    boolean existsByFornecedor_Id(UUID fornecedorId);
}
