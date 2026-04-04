package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Unidade;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.exception.RegraNegocioException;
import com.rainhacosmeticos.repository.UnidadeRepository;
import com.rainhacosmeticos.web.dto.UnidadeCreateRequest;
import com.rainhacosmeticos.web.dto.UnidadeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public List<UnidadeResponse> listar() {
        return unidadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UnidadeResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public UnidadeResponse criar(UnidadeCreateRequest request) {
        Unidade unidade = Unidade.builder()
                .nome(request.nome().trim())
                .sigla(request.sigla().trim().toUpperCase())
                .ativo(true)
                .build();
        return toResponse(unidadeRepository.save(unidade));
    }

    @Transactional
    public UnidadeResponse ativar(UUID id) {
        Unidade unidade = buscarEntidade(id);
        unidade.setAtivo(true);
        return toResponse(unidadeRepository.save(unidade));
    }

    @Transactional
    public UnidadeResponse desativar(UUID id) {
        Unidade unidade = buscarEntidade(id);
        unidade.setAtivo(false);
        return toResponse(unidadeRepository.save(unidade));
    }

    public Unidade buscarEntidadeAtiva(UUID id) {
        Unidade u = buscarEntidade(id);
        if (!u.isAtivo()) {
            throw new RegraNegocioException("Unidade inativa.");
        }
        return u;
    }

    public Unidade buscarEntidade(UUID id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada."));
    }

    private UnidadeResponse toResponse(Unidade u) {
        return new UnidadeResponse(u.getId(), u.getNome(), u.getSigla(), u.isAtivo());
    }
}
