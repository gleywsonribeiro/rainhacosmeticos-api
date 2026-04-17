package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rainhacosmeticos.domain.enums.FormaPagamentoVenda;
import com.rainhacosmeticos.domain.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record VendaResponse(
    UUID id,
    @JsonProperty("cliente_id") UUID clienteId,
    @JsonProperty("nome_cliente") String nomeCliente,
    @JsonProperty("data_venda") LocalDate dataVenda,
    @JsonProperty("forma_pagamento") FormaPagamentoVenda formaPagamento,
    StatusPagamento status,
    String observacao,
    List<ItemVendaResponse> itens,
    @JsonProperty("total_venda") BigDecimal totalVenda
) {
}
