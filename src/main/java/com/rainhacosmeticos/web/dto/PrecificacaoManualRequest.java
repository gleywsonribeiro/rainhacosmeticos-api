package com.rainhacosmeticos.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PrecificacaoManualRequest(

        @NotNull
        @JsonProperty("produto_id")
        UUID produtoId,

        @NotNull
        @JsonProperty("fornecedor_id")
        UUID fornecedorId,

        /**
         * Data de referência da precificação. Se não informada, usa a data atual.
         */
        @JsonProperty("data_referencia")
        LocalDate dataReferencia,

        /**
         * Custo unitário de aquisição do produto.
         */
        @NotNull
        @DecimalMin("0.01")
        @JsonProperty("custo")
        BigDecimal custo,

        /**
         * Fator de margem de lucro. Ex: 0.40 = 40% de margem.
         * precoSugerido = (custo + custosExtras) * (1 + multiplicadorMargem)
         */
        @NotNull
        @DecimalMin("0.0")
        @JsonProperty("multiplicador_margem")
        BigDecimal multiplicadorMargem,

        /**
         * Taxa adicional para pagamento em cartão. Ex: 0.10 = 10%.
         * precoCartao = precoSugerido * (1 + taxaCartao)
         * Se não informada, usa 10% como padrão.
         */
        @JsonProperty("taxa_cartao")
        BigDecimal taxaCartao,

        @JsonProperty("custo_embalagem")
        BigDecimal custoEmbalagem,

        @JsonProperty("custo_brinde")
        BigDecimal custoBrinde,

        @JsonProperty("custo_papelaria")
        BigDecimal custoPapelaria,

        @JsonProperty("outros_custos")
        BigDecimal outrosCustos
) {
}
