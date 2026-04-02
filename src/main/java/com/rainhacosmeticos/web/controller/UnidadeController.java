package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.UnidadeService;
import com.rainhacosmeticos.web.dto.UnidadeCreateRequest;
import com.rainhacosmeticos.web.dto.UnidadeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping
    public List<UnidadeResponse> listar() {
        return unidadeService.listar();
    }

    @GetMapping("/{id}")
    public UnidadeResponse buscar(@PathVariable Long id) {
        return unidadeService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnidadeResponse criar(@RequestBody @Valid UnidadeCreateRequest request) {
        return unidadeService.criar(request);
    }

    @PatchMapping("/{id}/ativar")
    public UnidadeResponse ativar(@PathVariable Long id) {
        return unidadeService.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public UnidadeResponse desativar(@PathVariable Long id) {
        return unidadeService.desativar(id);
    }
}
