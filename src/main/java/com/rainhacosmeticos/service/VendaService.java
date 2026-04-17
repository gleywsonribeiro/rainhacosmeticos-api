package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Cliente;
import com.rainhacosmeticos.domain.model.ItemVenda;
import com.rainhacosmeticos.domain.model.Produto;
import com.rainhacosmeticos.domain.model.Venda;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.exception.RegraNegocioException;
import com.rainhacosmeticos.repository.ClienteRepository;
import com.rainhacosmeticos.repository.ProdutoRepository;
import com.rainhacosmeticos.repository.VendaRepository;
import com.rainhacosmeticos.web.dto.ItemVendaRequest;
import com.rainhacosmeticos.web.dto.ItemVendaResponse;
import com.rainhacosmeticos.web.dto.VendaRequest;
import com.rainhacosmeticos.web.dto.VendaResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository,
                        ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public VendaResponse criar(VendaRequest request) {
        Cliente cliente = null;
        if (request.clienteId() != null) {
            cliente = clienteRepository.findById(request.clienteId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
        }

        Venda venda = Venda.builder()
                .cliente(cliente)
                .dataVenda(LocalDate.now())
                .formaPagamento(request.formaPagamento())
                .status(request.status())
                .observacao(request.observacao())
                .itens(new ArrayList<>())
                .build();

        for (ItemVendaRequest itemReq : request.itens()) {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + itemReq.produtoId()));

            if (!produto.isAtivo()) {
                throw new RegraNegocioException("O produto " + produto.getNome() + " está inativo e não pode ser vendido.");
            }

            if (produto.getQuantidadeEstoque() < itemReq.quantidade()) {
                throw new RegraNegocioException("Estoque insuficiente para o produto " + produto.getNome() + ".");
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemReq.quantidade());

            BigDecimal precoUnitario = produto.getPreco() != null ? produto.getPreco() : BigDecimal.ZERO;
            
            ItemVenda item = ItemVenda.builder()
                    .venda(venda)
                    .produto(produto)
                    .quantidade(itemReq.quantidade())
                    .precoUnitario(precoUnitario)
                    .desconto(itemReq.desconto() != null ? itemReq.desconto() : BigDecimal.ZERO)
                    .build();

            venda.getItens().add(item);
        }

        venda = vendaRepository.save(venda);
        return toResponse(venda);
    }

    public List<VendaResponse> listar() {
        return vendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public VendaResponse buscarPorId(UUID id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Venda não encontrada."));
        return toResponse(venda);
    }

    private VendaResponse toResponse(Venda venda) {
        List<ItemVendaResponse> itensResponse = venda.getItens().stream().map(item -> new ItemVendaResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getDesconto(),
                item.getSubtotal()
        )).toList();

        return new VendaResponse(
                venda.getId(),
                venda.getCliente() != null ? venda.getCliente().getId() : null,
                venda.getCliente() != null ? venda.getCliente().getNome() : null,
                venda.getDataVenda(),
                venda.getFormaPagamento(),
                venda.getStatus(),
                venda.getObservacao(),
                itensResponse,
                venda.getTotalVenda()
        );
    }
}
