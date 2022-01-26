package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PedidoFilter {

    private Optional<Long> clienteId = Optional.empty();
    private Optional<Long> restauranteId = Optional.empty();
    private Optional<OffsetDateTime> dataCriacaoInicio = Optional.empty();
    private Optional<OffsetDateTime> dataCriacaoFim = Optional.empty();

}
