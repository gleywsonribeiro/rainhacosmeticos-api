package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Precificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrecificacaoRepository extends JpaRepository<Precificacao, UUID> {

    boolean existsByFornecedor_Id(UUID fornecedorId);

    /** Busca todas as precificações de um produto, da mais recente para a mais antiga. */
    List<Precificacao> findByProduto_IdOrderByDataReferenciaDesc(UUID produtoId);

    /** Busca a precificação mais recente de um produto. */
    Optional<Precificacao> findTopByProduto_IdOrderByDataReferenciaDesc(UUID produtoId);

    /** Busca a precificação mais recente de um produto junto a um fornecedor específico. */
    Optional<Precificacao> findTopByProduto_IdAndFornecedor_IdOrderByDataReferenciaDesc(UUID produtoId, UUID fornecedorId);
}

