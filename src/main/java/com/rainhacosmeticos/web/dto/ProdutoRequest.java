package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProdutoRequest(
        @NotBlank String nome,
        String descricao,
        String ean,
        @JsonProperty("ativo") Boolean ativo,
        @NotNull @JsonProperty("categoria_id") UUID categoriaId,
        @NotNull @JsonProperty("unidade_id") UUID unidadeId
) {
}
