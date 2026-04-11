package com.rainhacosmeticos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 4000)
    private String descricao;

    @Column(unique = true)
    private String ean;

    /**
     * Preço de venda atual. Atualizado automaticamente pelo PrecificacaoService
     * sempre que uma nova Nota de Compra é processada.
     */
    @Column(nullable = false)
    @Builder.Default
    private BigDecimal preco = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativo = true;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantidadeEstoque = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
}
