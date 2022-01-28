package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("FROM Produto WHERE restaurante.id = :restauranteId AND id = :produtoId")
    Optional<Produto> findById(Long restauranteId, Long produtoId);

    @Query("SELECT f FROM FotoProduto f JOIN f.produto p WHERE p.restaurante.id = :restauranteId AND f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
