package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.ProdutoService;
import com.rainhacosmeticos.web.dto.ProdutoRequest;
import com.rainhacosmeticos.web.dto.ProdutoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<ProdutoResponse> listar() {
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscar(@PathVariable UUID id) {
        return produtoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse criar(@RequestBody @Valid ProdutoRequest request) {
        return produtoService.criar(request);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable UUID id, @RequestBody @Valid ProdutoRequest request) {
        return produtoService.atualizar(id, request);
    }

    @PatchMapping("/{id}/ativar")
    public ProdutoResponse ativar(@PathVariable UUID id) {
        return produtoService.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public ProdutoResponse desativar(@PathVariable UUID id) {
        return produtoService.desativar(id);
    }
}
