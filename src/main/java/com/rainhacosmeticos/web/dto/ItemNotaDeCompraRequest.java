package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemNotaDeCompraRequest(

        @NotNull @JsonProperty("produto_id") UUID produtoId,

        @NotNull @Min(1) Integer quantidade,

        @NotNull @DecimalMin("0.01") @JsonProperty("custo_unitario") BigDecimal custoUnitario,

        /**
         * Fator de margem de lucro. Ex: 0.40 = 40% de margem.
         * precoSugerido = (custoUnitario + custosExtras) * (1 + multiplicadorMargem)
         */
        @DecimalMin("0.0")
        @JsonProperty("multiplicador_margem")
        BigDecimal multiplicadorMargem,

        /**
         * Taxa adicional para pagamento em cartão. Ex: 0.10 = 10%.
         * precoCartao = precoSugerido * (1 + taxaCartao)
         */
        @JsonProperty("taxa_cartao") BigDecimal taxaCartao,

        @JsonProperty("custo_embalagem") BigDecimal custoEmbalagem,

        @JsonProperty("custo_brinde") BigDecimal custoBrinde,

        @JsonProperty("custo_papelaria") BigDecimal custoPapelaria,

        @JsonProperty("outros_custos") BigDecimal outrosCustos
) {
}
