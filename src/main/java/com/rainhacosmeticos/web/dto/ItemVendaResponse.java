package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaResponse(
    UUID id,
    @JsonProperty("produto_id") UUID produtoId,
    @JsonProperty("nome_produto") String nomeProduto,
    Integer quantidade,
    @JsonProperty("preco_unitario") BigDecimal precoUnitario,
    BigDecimal desconto,
    BigDecimal subtotal
) {
}
