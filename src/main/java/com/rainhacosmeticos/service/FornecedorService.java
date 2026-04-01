package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Endereco;
import com.rainhacosmeticos.domain.model.Fornecedor;
import com.rainhacosmeticos.exception.ExclusaoNaoPermitidaException;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.repository.FornecedorRepository;
import com.rainhacosmeticos.repository.NotaDeCompraRepository;
import com.rainhacosmeticos.repository.PrecificacaoRepository;
import com.rainhacosmeticos.web.dto.FornecedorEnderecoDto;
import com.rainhacosmeticos.web.dto.FornecedorRequest;
import com.rainhacosmeticos.web.dto.FornecedorResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final NotaDeCompraRepository notaDeCompraRepository;
    private final PrecificacaoRepository precificacaoRepository;

    public FornecedorService(
            FornecedorRepository fornecedorRepository,
            NotaDeCompraRepository notaDeCompraRepository,
            PrecificacaoRepository precificacaoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.notaDeCompraRepository = notaDeCompraRepository;
        this.precificacaoRepository = precificacaoRepository;
    }

    public List<FornecedorResponse> listar() {
        return fornecedorRepository.findAll().stream().map(this::toResponse).toList();
    }

    public FornecedorResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public FornecedorResponse criar(FornecedorRequest request) {
        Fornecedor fornecedor = Fornecedor.builder()
                .nome(request.nome().trim())
                .ie(trimToNull(request.ie()))
                .cnpj(trimToNull(request.cnpj()))
                .email(trimToNull(request.email()))
                .fone(trimToNull(request.fone()))
                .endereco(toEndereco(request.endereco()))
                .build();
        return toResponse(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public FornecedorResponse atualizar(UUID id, FornecedorRequest request) {
        Fornecedor fornecedor = buscarEntidade(id);
        fornecedor.setNome(request.nome().trim());
        fornecedor.setIe(trimToNull(request.ie()));
        fornecedor.setCnpj(trimToNull(request.cnpj()));
        fornecedor.setEmail(trimToNull(request.email()));
        fornecedor.setFone(trimToNull(request.fone()));
        fornecedor.setEndereco(toEndereco(request.endereco()));
        return toResponse(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public void excluir(UUID id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Fornecedor não encontrado.");
        }
        List<String> motivos = new ArrayList<>();
        if (notaDeCompraRepository.existsByFornecedor_Id(id)) {
            motivos.add("notas de compra");
        }
        if (precificacaoRepository.existsByFornecedor_Id(id)) {
            motivos.add("precificações");
        }
        if (!motivos.isEmpty()) {
            throw new ExclusaoNaoPermitidaException(
                    "Não é possível excluir o fornecedor: existem registros vinculados (" + String.join(", ", motivos) + ").");
        }
        fornecedorRepository.deleteById(id);
    }

    private Fornecedor buscarEntidade(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado."));
    }

    private FornecedorResponse toResponse(Fornecedor f) {
        return new FornecedorResponse(
                f.getId(),
                f.getNome(),
                f.getIe(),
                f.getCnpj(),
                f.getEmail(),
                f.getFone(),
                toFornecedorEnderecoDto(f.getEndereco()));
    }

    private static Endereco toEndereco(FornecedorEnderecoDto dto) {
        if (dto == null) {
            return null;
        }
        return Endereco.builder()
                .cep(trimToNull(dto.cep()))
                .logradouro(trimToNull(dto.logradouro()))
                .numero(trimToNull(dto.numero()))
                .complemento(trimToNull(dto.complemento()))
                .bairro(trimToNull(dto.bairro()))
                .cidade(trimToNull(dto.cidade()))
                .estado(trimToNull(dto.uf()))
                .build();
    }

    private static FornecedorEnderecoDto toFornecedorEnderecoDto(Endereco e) {
        if (e == null) {
            return null;
        }
        return new FornecedorEnderecoDto(
                e.getLogradouro(),
                e.getNumero(),
                e.getComplemento(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep());
    }

    private static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
