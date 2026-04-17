package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rainhacosmeticos.domain.enums.FormaPagamentoVenda;
import com.rainhacosmeticos.domain.enums.StatusPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record VendaRequest(
    @JsonProperty("cliente_id") UUID clienteId,
    @NotNull @JsonProperty("forma_pagamento") FormaPagamentoVenda formaPagamento,
    @NotNull StatusPagamento status,
    String observacao,
    @NotEmpty @Valid List<ItemVendaRequest> itens
) {
}
