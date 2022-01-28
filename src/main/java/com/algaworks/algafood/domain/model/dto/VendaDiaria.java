package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class VendaDiaria {

    private final Date data;
    private final Long totalVendas;
    private final BigDecimal totalFaturado;

}
