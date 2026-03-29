package com.rainhacosmeticos.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteRequest(
        @NotBlank String nome,
        String email,
        String whatsapp,
        LocalDate dataNascimento,
        EnderecoDto endereco
) {
}
