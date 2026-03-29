package com.rainhacosmeticos.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nome,
        String apelido,
        String email,
        String whatsapp,
        LocalDate dataNascimento
) {
}
