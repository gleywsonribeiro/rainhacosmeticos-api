package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;

public record FornecedorRequest(@NotBlank String nome) {
}
