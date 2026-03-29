package com.rainhacosmeticos.service;

import com.rainhacosmeticos.domain.model.Cliente;
import com.rainhacosmeticos.exception.ExclusaoNaoPermitidaException;
import com.rainhacosmeticos.exception.RecursoNaoEncontradoException;
import com.rainhacosmeticos.repository.ClienteRepository;
import com.rainhacosmeticos.repository.VendaRepository;
import com.rainhacosmeticos.web.dto.ClienteRequest;
import com.rainhacosmeticos.web.dto.ClienteResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final VendaRepository vendaRepository;

    public ClienteService(ClienteRepository clienteRepository, VendaRepository vendaRepository) {
        this.clienteRepository = clienteRepository;
        this.vendaRepository = vendaRepository;
    }

    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ClienteResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nome(request.nome().trim())
                .apelido(trimToNull(request.apelido()))
                .email(trimToNull(request.email()))
                .whatsapp(trimToNull(request.whatsapp()))
                .dataNascimento(request.dataNascimento())
                .build();
        return toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteResponse atualizar(UUID id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);
        cliente.setNome(request.nome().trim());
        cliente.setApelido(trimToNull(request.apelido()));
        cliente.setEmail(trimToNull(request.email()));
        cliente.setWhatsapp(trimToNull(request.whatsapp()));
        cliente.setDataNascimento(request.dataNascimento());
        return toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public void excluir(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }
        if (vendaRepository.existsByCliente_Id(id)) {
            throw new ExclusaoNaoPermitidaException(
                    "Não é possível excluir o cliente: existem vendas vinculadas.");
        }
        clienteRepository.deleteById(id);
    }

    private Cliente buscarEntidade(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNome(), c.getApelido(), c.getEmail(), c.getWhatsapp(),
                c.getDataNascimento());
    }

    private static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
