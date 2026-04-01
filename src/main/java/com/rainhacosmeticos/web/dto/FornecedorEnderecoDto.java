package com.rainhacosmeticos.web.dto;

public record FornecedorEnderecoDto(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
) {
}
