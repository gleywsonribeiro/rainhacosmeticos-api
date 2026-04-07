package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("nome") String nome,
        @JsonProperty("descricao") String descricao,
        @JsonProperty("ean") String ean,
        @JsonProperty("preco") BigDecimal preco,
        @JsonProperty("ativo") boolean ativo,
        @JsonProperty("categoria_id") UUID categoriaId,
        @JsonProperty("unidade_id") UUID unidadeId
) {
}
