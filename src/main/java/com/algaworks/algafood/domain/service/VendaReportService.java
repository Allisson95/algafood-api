package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] execute(VendaDiariaFilter filter, String timeOffset);

}
