package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnidadeCreateRequest(
        @NotBlank String nome,
        @NotBlank @Size(max = 5) String sigla
) {
}
