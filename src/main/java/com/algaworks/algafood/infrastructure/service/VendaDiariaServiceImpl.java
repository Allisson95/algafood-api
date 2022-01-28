package com.algaworks.algafood.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaService;

import org.springframework.stereotype.Repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Repository
public class VendaDiariaServiceImpl implements VendaDiariaService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> execute(VendaDiariaFilter filter, String timeOffset) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);

        Root<Pedido> root = query.from(Pedido.class);

        Expression<Date> convertTz = MySQLFunctions.convertTz(builder, root.get("dataCriacao"), "+00:00", timeOffset);

        Expression<Date> dateFunction = builder.function("date", Date.class, convertTz);

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                dateFunction,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        Predicate[] predicates = predicates(filter, root, builder);

        query.select(selection);
        query.where(predicates);
        query.groupBy(dateFunction);

        return manager.createQuery(query).getResultList();
    }

    private Predicate[] predicates(VendaDiariaFilter filter, Root<Pedido> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>(1);

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        filter.getRestauranteId().ifPresent(restauranteId -> {
            predicates.add(builder.equal(root.get("restaurante"), restauranteId));
        });

        filter.getDataCriacaoInicio().ifPresent(dataCriacaoInicio -> {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataCriacaoInicio));
        });

        filter.getDataCriacaoFim().ifPresent(dataCriacaoFim -> {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), dataCriacaoFim));
        });

        return predicates.toArray(Predicate[]::new);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MySQLFunctions {

        public static Expression<Date> convertTz(CriteriaBuilder builder, Expression<?> dateAttr, String fromTimeOffset, String toTimeOffset) {
            return builder.function("convert_tz", Date.class, dateAttr, builder.literal(fromTimeOffset), builder.literal(toTimeOffset));
        }

        public static Expression<Date> date(CriteriaBuilder builder, Expression<?> dateAttr) {
            return builder.function("date", Date.class, dateAttr);
        }

    }

}
