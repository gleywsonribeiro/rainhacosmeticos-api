package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CategoriaCreateRequest(
        @NotBlank String nome,
        @JsonProperty("margem_padrao")
        BigDecimal margemPadrao
) {
}
