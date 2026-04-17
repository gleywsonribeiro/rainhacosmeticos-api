package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.VendaService;
import com.rainhacosmeticos.web.dto.VendaRequest;
import com.rainhacosmeticos.web.dto.VendaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponse criar(@RequestBody @Valid VendaRequest request) {
        return vendaService.criar(request);
    }

    @GetMapping
    public List<VendaResponse> listar() {
        return vendaService.listar();
    }

    @GetMapping("/{id}")
    public VendaResponse buscarPorId(@PathVariable UUID id) {
        return vendaService.buscarPorId(id);
    }
}
