package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.PrecificacaoService;
import com.rainhacosmeticos.web.dto.PrecificacaoManualRequest;
import com.rainhacosmeticos.web.dto.PrecificacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Cria uma precificação manual para um produto, sem necessidade de Nota de Compra.
     * Útil para precificar produtos recém-cadastrados ou ajustar preços em qualquer momento.
     */
    @PostMapping
    public ResponseEntity<PrecificacaoResponse> registrarManualmente(
            @Valid @RequestBody PrecificacaoManualRequest request) {
        PrecificacaoResponse response = precificacaoService.registrarManualmente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
