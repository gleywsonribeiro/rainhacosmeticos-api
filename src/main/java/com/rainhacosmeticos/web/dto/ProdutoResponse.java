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
        @JsonProperty("preco_custo") BigDecimal precoCusto,
        @JsonProperty("margem_lucro") BigDecimal margemLucro,
        @JsonProperty("ativo") boolean ativo,
        @JsonProperty("categoria_id") Long categoriaId,
        @JsonProperty("unidade_id") Long unidadeId
) {
}
