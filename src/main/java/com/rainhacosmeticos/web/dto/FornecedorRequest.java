package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;

public record FornecedorRequest(
        @NotBlank String nome,
        String ie,
        String cnpj,
        String email,
        String fone,
        FornecedorEnderecoDto endereco
) {
}
