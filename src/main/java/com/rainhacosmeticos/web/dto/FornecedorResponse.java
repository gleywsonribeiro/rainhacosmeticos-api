package com.rainhacosmeticos.web.dto;

import java.util.UUID;

public record FornecedorResponse(
        UUID id,
        String nome,
        String ie,
        String cnpj,
        String email,
        String fone,
        FornecedorEnderecoDto endereco
) {
}
