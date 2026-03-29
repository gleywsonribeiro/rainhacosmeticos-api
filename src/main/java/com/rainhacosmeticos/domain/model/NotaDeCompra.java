package com.rainhacosmeticos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private LocalDate dataCompra;

    @Column(nullable = false)
    private BigDecimal totalPago;

    @Column(nullable = false)
    private String formaPagamento;

    @Builder.Default
    @Column(nullable = false)
    private Integer parcelas = 1;

    private String observacao;

    @OneToMany(mappedBy = "notaDeCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemNotaDeCompra> itens = new ArrayList<>();
}
