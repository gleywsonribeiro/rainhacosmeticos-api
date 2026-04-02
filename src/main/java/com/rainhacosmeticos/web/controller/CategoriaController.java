package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.CategoriaService;
import com.rainhacosmeticos.web.dto.CategoriaCreateRequest;
import com.rainhacosmeticos.web.dto.CategoriaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponse buscar(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse criar(@RequestBody @Valid CategoriaCreateRequest request) {
        return categoriaService.criar(request);
    }

    @PatchMapping("/{id}/ativar")
    public CategoriaResponse ativar(@PathVariable Long id) {
        return categoriaService.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public CategoriaResponse desativar(@PathVariable Long id) {
        return categoriaService.desativar(id);
    }
}
