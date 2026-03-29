package com.rainhacosmeticos.web.dto;

public record EnderecoDto(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
) {
}

