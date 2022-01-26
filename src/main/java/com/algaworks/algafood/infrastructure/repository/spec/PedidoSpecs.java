package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoFilter;

import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecs {
    
    public static Specification<Pedido> filter(PedidoFilter filter) {
        return (root, query, builder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            List<Predicate> predicates = new ArrayList<>(0);

            filter.getClienteId().ifPresent(clienteId -> {
                predicates.add(builder.equal(root.get("cliente"), clienteId));
            });

            filter.getRestauranteId().ifPresent(restauranteId -> {
                predicates.add(builder.equal(root.get("restaurante"), restauranteId));
            });

            filter.getDataCriacaoInicio().ifPresent(dataCriacaoInicio -> {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataCriacaoInicio));
            });

            filter.getDataCriacaoFim().ifPresent(dataCriacaoFim -> {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), dataCriacaoFim));
            });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
