package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Fornecedor;
import com.rainhacosmeticos.domain.model.ItemNotaDeCompra;
import com.rainhacosmeticos.domain.model.NotaDeCompra;
import com.rainhacosmeticos.domain.model.Produto;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.repository.FornecedorRepository;
import com.rainhacosmeticos.repository.NotaDeCompraRepository;
import com.rainhacosmeticos.repository.ProdutoRepository;
import com.rainhacosmeticos.web.dto.ItemNotaDeCompraRequest;
import com.rainhacosmeticos.web.dto.ItemNotaDeCompraResponse;
import com.rainhacosmeticos.web.dto.NotaDeCompraRequest;
import com.rainhacosmeticos.web.dto.NotaDeCompraResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class NotaDeCompraService {

    private final NotaDeCompraRepository notaDeCompraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final PrecificacaoService precificacaoService;

    public NotaDeCompraService(
            NotaDeCompraRepository notaDeCompraRepository,
            FornecedorRepository fornecedorRepository,
            ProdutoRepository produtoRepository,
            PrecificacaoService precificacaoService) {
        this.notaDeCompraRepository = notaDeCompraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.precificacaoService = precificacaoService;
    }

    public List<NotaDeCompraResponse> listar() {
        return notaDeCompraRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public NotaDeCompraResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    /**
     * Registra uma Nota de Compra e dispara a precificação automática para cada item.
     *
     * <p>Fluxo:
     * <ol>
     *   <li>Valida e carrega o Fornecedor</li>
     *   <li>Constrói a NotaDeCompra e seus itens</li>
     *   <li>Persiste tudo em cascata</li>
     *   <li>Para cada item, cria uma Precificacao e atualiza Produto.preco</li>
     * </ol>
     */
    @Transactional
    public NotaDeCompraResponse registrar(NotaDeCompraRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(request.fornecedorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado."));

        // Monta a nota sem itens primeiro para ter referência ao salvar os itens
        NotaDeCompra nota = NotaDeCompra.builder()
                .fornecedor(fornecedor)
                .dataCompra(request.dataCompra())
                .totalPago(request.totalPago())
                .formaPagamento(request.formaPagamento())
                .parcelas(request.parcelas() != null ? request.parcelas() : 1)
                .observacao(request.observacao())
                .build();

        // Monta e vincula os itens
        for (ItemNotaDeCompraRequest itemReq : request.itens()) {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto não encontrado: " + itemReq.produtoId()));

            ItemNotaDeCompra item = ItemNotaDeCompra.builder()
                    .notaDeCompra(nota)
                    .produto(produto)
                    .quantidade(itemReq.quantidade())
                    .custoUnitario(itemReq.custoUnitario())
                    .multiplicadorMargem(itemReq.multiplicadorMargem())
                    .taxaCartao(itemReq.taxaCartao() != null ? itemReq.taxaCartao() : BigDecimal.valueOf(0.10))
                    .custoEmbalagem(itemReq.custoEmbalagem() != null ? itemReq.custoEmbalagem() : BigDecimal.ZERO)
                    .custoBrinde(itemReq.custoBrinde() != null ? itemReq.custoBrinde() : BigDecimal.ZERO)
                    .custoPapelaria(itemReq.custoPapelaria() != null ? itemReq.custoPapelaria() : BigDecimal.ZERO)
                    .outrosCustos(itemReq.outrosCustos() != null ? itemReq.outrosCustos() : BigDecimal.ZERO)
                    .build();

            nota.getItens().add(item);
        }

        // Salva a nota com todos os itens em cascata
        NotaDeCompra notaSalva = notaDeCompraRepository.save(nota);

        // Processa a precificação para cada item salvo
        for (ItemNotaDeCompra item : notaSalva.getItens()) {
            precificacaoService.processarItem(item, item.getProduto(), fornecedor);
        }

        return toResponse(notaSalva);
    }

    private NotaDeCompra buscarEntidade(UUID id) {
        return notaDeCompraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Nota de compra não encontrada."));
    }

    private NotaDeCompraResponse toResponse(NotaDeCompra nota) {
        List<ItemNotaDeCompraResponse> itensResponse = nota.getItens().stream()
                .map(this::toItemResponse)
                .toList();

        return new NotaDeCompraResponse(
                nota.getId(),
                nota.getFornecedor().getId(),
                nota.getFornecedor().getNome(),
                nota.getDataCompra(),
                nota.getTotalPago(),
                nota.getFormaPagamento(),
                nota.getParcelas(),
                nota.getObservacao(),
                itensResponse
        );
    }

    private ItemNotaDeCompraResponse toItemResponse(ItemNotaDeCompra item) {
        return new ItemNotaDeCompraResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getCustoUnitario(),
                item.getMultiplicadorMargem(),
                item.getTaxaCartao(),
                item.getCustoEmbalagem(),
                item.getCustoBrinde(),
                item.getCustoPapelaria(),
                item.getOutrosCustos()
        );
    }
}
