package com.rainhacosmeticos.web.dto;

import java.util.UUID;

public record CategoriaResponse(UUID id, String nome, boolean ativo) {
}
