package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NotaDeCompraRequest(

        @NotNull @JsonProperty("fornecedor_id") UUID fornecedorId,

        @NotNull @JsonProperty("data_compra") LocalDate dataCompra,

        @NotNull @DecimalMin("0.01") @JsonProperty("total_pago") BigDecimal totalPago,

        @NotBlank @JsonProperty("forma_pagamento") String formaPagamento,

        @JsonProperty("parcelas") Integer parcelas,

        @JsonProperty("observacao") String observacao,

        @NotEmpty @Valid @JsonProperty("itens") List<ItemNotaDeCompraRequest> itens
) {
}
