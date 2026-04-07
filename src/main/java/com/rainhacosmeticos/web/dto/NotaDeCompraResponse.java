package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NotaDeCompraResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("fornecedor_id") UUID fornecedorId,
        @JsonProperty("fornecedor_nome") String fornecedorNome,
        @JsonProperty("data_compra") LocalDate dataCompra,
        @JsonProperty("total_pago") BigDecimal totalPago,
        @JsonProperty("forma_pagamento") String formaPagamento,
        @JsonProperty("parcelas") Integer parcelas,
        @JsonProperty("observacao") String observacao,
        @JsonProperty("itens") List<ItemNotaDeCompraResponse> itens
) {
}
