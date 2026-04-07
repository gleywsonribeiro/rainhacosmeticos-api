package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.NotaDeCompraService;
import com.rainhacosmeticos.web.dto.NotaDeCompraRequest;
import com.rainhacosmeticos.web.dto.NotaDeCompraResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notas-de-compra")
public class NotaDeCompraController {

    private final NotaDeCompraService notaDeCompraService;

    public NotaDeCompraController(NotaDeCompraService notaDeCompraService) {
        this.notaDeCompraService = notaDeCompraService;
    }

    @GetMapping
    public List<NotaDeCompraResponse> listar() {
        return notaDeCompraService.listar();
    }

    @GetMapping("/{id}")
    public NotaDeCompraResponse buscar(@PathVariable UUID id) {
        return notaDeCompraService.buscarPorId(id);
    }

    /**
     * Registra uma nova Nota de Compra.
     * Ao registrar, o sistema cria automaticamente uma Precificacao para cada item
     * e atualiza o preco de venda do respectivo Produto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaDeCompraResponse registrar(@RequestBody @Valid NotaDeCompraRequest request) {
        return notaDeCompraService.registrar(request);
    }
}
