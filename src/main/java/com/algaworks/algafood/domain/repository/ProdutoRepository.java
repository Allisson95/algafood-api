package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import com.algaworks.algafood.domain.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("FROM Produto WHERE restaurante.id = :restauranteId AND id = :produtoId")
    Optional<Produto> findById(Long restauranteId, Long produtoId);

}
