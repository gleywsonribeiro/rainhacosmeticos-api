package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PrecificacaoResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("produto_id") UUID produtoId,
        @JsonProperty("produto_nome") String produtoNome,
        @JsonProperty("fornecedor_id") UUID fornecedorId,
        @JsonProperty("fornecedor_nome") String fornecedorNome,
        @JsonProperty("data_referencia") LocalDate dataReferencia,
        @JsonProperty("custo") BigDecimal custo,
        @JsonProperty("custo_embalagem") BigDecimal custoEmbalagem,
        @JsonProperty("custo_brinde") BigDecimal custoBrinde,
        @JsonProperty("custo_papelaria") BigDecimal custoPapelaria,
        @JsonProperty("outros_custos") BigDecimal outrosCustos,
        @JsonProperty("total_custo") BigDecimal totalCusto,
        @JsonProperty("multiplicador_margem") BigDecimal multiplicadorMargem,
        @JsonProperty("taxa_cartao") BigDecimal taxaCartao,
        @JsonProperty("preco_sugerido") BigDecimal precoSugerido,
        @JsonProperty("preco_cartao") BigDecimal precoCartao
) {
}
