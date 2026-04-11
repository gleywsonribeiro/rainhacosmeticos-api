package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Categoria;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.exception.RegraNegocioException;
import com.rainhacosmeticos.repository.CategoriaRepository;
import com.rainhacosmeticos.web.dto.CategoriaCreateRequest;
import com.rainhacosmeticos.web.dto.CategoriaResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoriaResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public CategoriaResponse criar(CategoriaCreateRequest request) {
        Categoria categoria = Categoria.builder()
                .nome(request.nome().trim())
                .margemPadrao(request.margemPadrao() != null ? request.margemPadrao() : java.math.BigDecimal.valueOf(0.40))
                .ativo(true)
                .build();
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse ativar(UUID id) {
        Categoria categoria = buscarEntidade(id);
        categoria.setAtivo(true);
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse desativar(UUID id) {
        Categoria categoria = buscarEntidade(id);
        categoria.setAtivo(false);
        return toResponse(categoriaRepository.save(categoria));
    }

    public Categoria buscarEntidadeAtiva(UUID id) {
        Categoria c = buscarEntidade(id);
        if (!c.isAtivo()) {
            throw new RegraNegocioException("Categoria inativa.");
        }
        return c;
    }

    public Categoria buscarEntidade(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));
    }

    private CategoriaResponse toResponse(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNome(), c.isAtivo(), c.getMargemPadrao());
    }
}
