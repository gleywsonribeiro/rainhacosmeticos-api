package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.PrecificacaoService;
import com.rainhacosmeticos.web.dto.PrecificacaoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/precificacoes")
public class PrecificacaoController {

    private final PrecificacaoService precificacaoService;

    public PrecificacaoController(PrecificacaoService precificacaoService) {
        this.precificacaoService = precificacaoService;
    }

    /**
     * Retorna o histórico completo de precificações de um produto,
     * ordenado do mais recente para o mais antigo.
     */
    @GetMapping("/produto/{produtoId}")
    public List<PrecificacaoResponse> listarPorProduto(@PathVariable UUID produtoId) {
        return precificacaoService.listarPorProduto(produtoId);
    }

    /**
     * Retorna a precificação vigente (mais recente) de um produto.
     */
    @GetMapping("/produto/{produtoId}/vigente")
    public PrecificacaoResponse buscarVigente(@PathVariable UUID produtoId) {
        return precificacaoService.buscarVigente(produtoId);
    }
}
