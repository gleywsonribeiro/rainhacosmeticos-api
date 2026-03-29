package com.rainhacosmeticos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Precificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private LocalDate dataReferencia;

    @Column(nullable = false)
    private BigDecimal custo;

    @Builder.Default
    private BigDecimal custoEmbalagem = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal custoBrinde = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal custoPapelaria = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal outrosCustos = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal multiplicadorMargem;

    @Column(nullable = false)
    private BigDecimal precoSugerido;

    @Column(nullable = false)
    private BigDecimal precoCartao;

    @Transient
    public BigDecimal getTotalCusto() {
        return custo
                .add(custoEmbalagem != null ? custoEmbalagem : BigDecimal.ZERO)
                .add(custoBrinde != null ? custoBrinde : BigDecimal.ZERO)
                .add(custoPapelaria != null ? custoPapelaria : BigDecimal.ZERO)
                .add(outrosCustos != null ? outrosCustos : BigDecimal.ZERO);
    }

    @PrePersist
    @PreUpdate
    public void calcularPrecos() {
        BigDecimal totalCusto = getTotalCusto();
        
        // precoSugerido = totalCusto * (1 + multiplicadorMargem)
        this.precoSugerido = totalCusto.multiply(BigDecimal.ONE.add(multiplicadorMargem));
        
        // precoCartao - assuming a 10% tax if not specified, or just a placeholder logic
        // For now, I'll use 10% (1.1) as a placeholder for "taxa para pagamento em cartão"
        this.precoCartao = this.precoSugerido.multiply(BigDecimal.valueOf(1.1));
    }
}
