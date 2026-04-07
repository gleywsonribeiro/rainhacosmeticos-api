package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Fornecedor;
import com.rainhacosmeticos.domain.model.ItemNotaDeCompra;
import com.rainhacosmeticos.domain.model.Precificacao;
import com.rainhacosmeticos.domain.model.Produto;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.repository.PrecificacaoRepository;
import com.rainhacosmeticos.repository.ProdutoRepository;
import com.rainhacosmeticos.web.dto.PrecificacaoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PrecificacaoService {

    private final PrecificacaoRepository precificacaoRepository;
    private final ProdutoRepository produtoRepository;

    public PrecificacaoService(
            PrecificacaoRepository precificacaoRepository,
            ProdutoRepository produtoRepository) {
        this.precificacaoRepository = precificacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Cria uma nova Precificacao a partir de um ItemNotaDeCompra processado.
     * Após salvar (o @PrePersist calcula precoSugerido e precoCartao),
     * atualiza o Produto.preco com o precoSugerido mais recente.
     */
    @Transactional
    public Precificacao processarItem(ItemNotaDeCompra item, Produto produto, Fornecedor fornecedor) {
        Precificacao precificacao = Precificacao.builder()
                .produto(produto)
                .fornecedor(fornecedor)
                .dataReferencia(item.getNotaDeCompra().getDataCompra())
                .custo(item.getCustoUnitario())
                .multiplicadorMargem(item.getMultiplicadorMargem())
                .taxaCartao(item.getTaxaCartao() != null ? item.getTaxaCartao() : BigDecimal.valueOf(0.10))
                .custoEmbalagem(item.getCustoEmbalagem() != null ? item.getCustoEmbalagem() : BigDecimal.ZERO)
                .custoBrinde(item.getCustoBrinde() != null ? item.getCustoBrinde() : BigDecimal.ZERO)
                .custoPapelaria(item.getCustoPapelaria() != null ? item.getCustoPapelaria() : BigDecimal.ZERO)
                .outrosCustos(item.getOutrosCustos() != null ? item.getOutrosCustos() : BigDecimal.ZERO)
                // precoSugerido e precoCartao serão calculados pelo @PrePersist
                .precoSugerido(BigDecimal.ZERO)
                .precoCartao(BigDecimal.ZERO)
                .build();

        Precificacao salva = precificacaoRepository.save(precificacao);

        // Atualiza o preço de venda do produto com o precoSugerido recém-calculado
        produto.setPreco(salva.getPrecoSugerido());
        produtoRepository.save(produto);

        return salva;
    }

    /** Retorna o histórico completo de precificações de um produto, do mais recente ao mais antigo. */
    public List<PrecificacaoResponse> listarPorProduto(UUID produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado.");
        }
        return precificacaoRepository
                .findByProduto_IdOrderByDataReferenciaDesc(produtoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /** Retorna a precificação mais recente (vigente) de um produto. */
    public PrecificacaoResponse buscarVigente(UUID produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado.");
        }
        return precificacaoRepository
                .findByProduto_IdOrderByDataReferenciaDesc(produtoId)
                .stream()
                .findFirst()
                .map(this::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhuma precificação encontrada para este produto."));
    }

    PrecificacaoResponse toResponse(Precificacao p) {
        return new PrecificacaoResponse(
                p.getId(),
                p.getProduto().getId(),
                p.getProduto().getNome(),
                p.getFornecedor().getId(),
                p.getFornecedor().getNome(),
                p.getDataReferencia(),
                p.getCusto(),
                p.getCustoEmbalagem(),
                p.getCustoBrinde(),
                p.getCustoPapelaria(),
                p.getOutrosCustos(),
                p.getTotalCusto(),
                p.getMultiplicadorMargem(),
                p.getTaxaCartao(),
                p.getPrecoSugerido(),
                p.getPrecoCartao()
        );
    }
}
