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
public class ItemNotaDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_de_compra_id", nullable = false)
    private NotaDeCompra notaDeCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    /** Preço pago por unidade ao fornecedor nesta NF. */
    @Column(nullable = false)
    private BigDecimal custoUnitario;

    /**
     * Fator de margem de lucro para este produto.
     * Exemplo: 0.40 = 40% de margem.
     */
    @Column(nullable = false)
    private BigDecimal multiplicadorMargem;

    /**
     * Taxa adicional para pagamento em cartão.
     * Exemplo: 0.10 = 10%.
     */
    @Column(nullable = false)
    @Builder.Default
    private BigDecimal taxaCartao = BigDecimal.valueOf(0.10);

    @Builder.Default
    private BigDecimal custoEmbalagem = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal custoBrinde = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal custoPapelaria = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal outrosCustos = BigDecimal.ZERO;
}

