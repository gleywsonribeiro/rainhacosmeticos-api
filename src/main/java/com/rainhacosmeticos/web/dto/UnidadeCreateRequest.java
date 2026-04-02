package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UnidadeCreateRequest(@NotBlank String nome) {
}
