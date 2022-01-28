package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaService;
import com.algaworks.algafood.domain.service.VendaReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private VendaDiariaService vendaDiariaService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<VendaDiaria> vendaDiarias(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaDiariaService.execute(filter, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = { MediaType.APPLICATION_PDF_VALUE })
    public ResponseEntity<byte[]> vendaDiariasPdf(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] vendasDiariasReport = vendaReportService.execute(filter, timeOffset);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas_diarias.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return ResponseEntity.ok()
            .headers(headers)
            .body(vendasDiariasReport);
    }

}
