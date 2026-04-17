package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaRequest(
    @NotNull @JsonProperty("produto_id") UUID produtoId,
    @NotNull @Min(1) Integer quantidade,
    BigDecimal desconto
) {
}
