package com.rainhacosmeticos.repository;

import com.rainhacosmeticos.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
