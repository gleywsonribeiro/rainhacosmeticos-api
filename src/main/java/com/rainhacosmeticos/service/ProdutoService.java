package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Produto;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.exception.RegistroDuplicadoException;
import com.rainhacosmeticos.repository.ProdutoRepository;
import com.rainhacosmeticos.web.dto.ProdutoRequest;
import com.rainhacosmeticos.web.dto.ProdutoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final UnidadeService unidadeService;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaService categoriaService,
            UnidadeService unidadeService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
        this.unidadeService = unidadeService;
    }

    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProdutoResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {
        String ean = trimToNull(request.ean());
        assertEanDisponivel(ean, null);

        boolean ativo = request.ativo() != null ? request.ativo() : true;

        Produto produto = Produto.builder()
                .nome(request.nome().trim())
                .descricao(trimToNull(request.descricao()))
                .ean(ean)
                .preco(request.preco())
                .precoCusto(request.precoCusto())
                .margemLucro(request.margemLucro())
                .ativo(ativo)
                .categoria(categoriaService.buscarEntidadeAtiva(request.categoriaId()))
                .unidade(unidadeService.buscarEntidadeAtiva(request.unidadeId()))
                .build();

        return toResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(UUID id, ProdutoRequest request) {
        Produto produto = buscarEntidade(id);
        String ean = trimToNull(request.ean());
        assertEanDisponivel(ean, id);

        produto.setNome(request.nome().trim());
        produto.setDescricao(trimToNull(request.descricao()));
        produto.setEan(ean);
        produto.setPreco(request.preco());
        produto.setPrecoCusto(request.precoCusto());
        produto.setMargemLucro(request.margemLucro());
        if (request.ativo() != null) {
            produto.setAtivo(request.ativo());
        }
        produto.setCategoria(categoriaService.buscarEntidadeAtiva(request.categoriaId()));
        produto.setUnidade(unidadeService.buscarEntidadeAtiva(request.unidadeId()));

        return toResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse ativar(UUID id) {
        Produto produto = buscarEntidade(id);
        produto.setAtivo(true);
        return toResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse desativar(UUID id) {
        Produto produto = buscarEntidade(id);
        produto.setAtivo(false);
        return toResponse(produtoRepository.save(produto));
    }

    private Produto buscarEntidade(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));
    }

    private ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getEan(),
                p.getPreco(),
                p.getPrecoCusto(),
                p.getMargemLucro(),
                p.isAtivo(),
                p.getCategoria().getId(),
                p.getUnidade().getId());
    }

    private void assertEanDisponivel(String ean, UUID ignorarId) {
        if (ean == null) {
            return;
        }
        produtoRepository.findByEan(ean).ifPresent(outro -> {
            if (ignorarId == null || !outro.getId().equals(ignorarId)) {
                throw new RegistroDuplicadoException("Já existe produto com este EAN.");
            }
        });
    }

    private static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
