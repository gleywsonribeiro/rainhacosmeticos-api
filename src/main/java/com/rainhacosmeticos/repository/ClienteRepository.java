package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
