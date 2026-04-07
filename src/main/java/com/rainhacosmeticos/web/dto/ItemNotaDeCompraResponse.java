package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemNotaDeCompraResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("produto_id") UUID produtoId,
        @JsonProperty("produto_nome") String produtoNome,
        @JsonProperty("quantidade") Integer quantidade,
        @JsonProperty("custo_unitario") BigDecimal custoUnitario,
        @JsonProperty("multiplicador_margem") BigDecimal multiplicadorMargem,
        @JsonProperty("taxa_cartao") BigDecimal taxaCartao,
        @JsonProperty("custo_embalagem") BigDecimal custoEmbalagem,
        @JsonProperty("custo_brinde") BigDecimal custoBrinde,
        @JsonProperty("custo_papelaria") BigDecimal custoPapelaria,
        @JsonProperty("outros_custos") BigDecimal outrosCustos
) {
}
