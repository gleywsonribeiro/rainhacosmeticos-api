package com.rainhacosmeticos.web.controller;

import com.rainhacosmeticos.service.ClienteService;
import com.rainhacosmeticos.web.dto.ClienteRequest;
import com.rainhacosmeticos.web.dto.ClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable UUID id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<ClienteResponse> buscarPorNome(@RequestParam("nome") String nome) {
        return clienteService.buscarPorNome(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criar(@RequestBody @Valid ClienteRequest request) {
        return clienteService.criar(request);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable UUID id, @RequestBody @Valid ClienteRequest request) {
        return clienteService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        clienteService.excluir(id);
    }
}
