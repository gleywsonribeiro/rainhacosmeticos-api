package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaCreateRequest(@NotBlank String nome) {
}
