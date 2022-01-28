package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PedidoFilter {

    private Optional<Long> clienteId = Optional.empty();
    private Optional<Long> restauranteId = Optional.empty();

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Optional<OffsetDateTime> dataCriacaoInicio = Optional.empty();

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Optional<OffsetDateTime> dataCriacaoFim = Optional.empty();

}
