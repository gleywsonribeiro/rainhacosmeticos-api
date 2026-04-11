package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoriaResponse(
        UUID id,
        String nome,
        boolean ativo,
        @JsonProperty("margem_padrao")
        BigDecimal margemPadrao
) {
}
