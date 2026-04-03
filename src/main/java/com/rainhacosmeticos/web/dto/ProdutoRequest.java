package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoRequest(
        @NotBlank String nome,
        String descricao,
        String ean,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) @JsonProperty("preco") BigDecimal preco,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) @JsonProperty("preco_custo") BigDecimal precoCusto,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) @JsonProperty("margem_lucro") BigDecimal margemLucro,
        @JsonProperty("ativo") Boolean ativo,
        @NotNull @JsonProperty("categoria_id") UUID categoriaId,
        @NotNull @JsonProperty("unidade_id") UUID unidadeId
) {
}
