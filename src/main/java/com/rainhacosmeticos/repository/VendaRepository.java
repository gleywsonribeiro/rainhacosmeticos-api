package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {

    boolean existsByCliente_Id(UUID clienteId);
}
