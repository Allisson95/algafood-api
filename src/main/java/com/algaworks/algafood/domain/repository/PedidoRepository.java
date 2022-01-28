package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.algaworks.algafood.domain.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("FROM Pedido p JOIN FETCH p.restaurante r JOIN FETCH p.formaPagamento JOIN FETCH p.cliente JOIN FETCH r.cozinha")
    List<Pedido> findAll();

    Optional<Pedido> findByCodigo(UUID codigoPedido);

}
