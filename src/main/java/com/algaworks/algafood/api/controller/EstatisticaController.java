package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.api.AlgaLinks.linkToEstatisticasVendasDiarias;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.algaworks.algafood.api.openapi.controller.EstatisticaControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaService;
import com.algaworks.algafood.domain.service.VendaReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController implements EstatisticaControllerOpenApi {

    @Autowired
    private VendaDiariaService vendaDiariaService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping
    @Override
    public ResponseEntity<EstatisticasModel> estatisticas() {
        EstatisticasModel root = new EstatisticasModel();

        root.add(
                linkToEstatisticasVendasDiarias(LinkRelation.of("vendas-diarias"))
            );

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS).cachePublic())
            .body(root);
    }

    @GetMapping(path = "/vendas-diarias", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Override
    public List<VendaDiaria> vendaDiarias(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaDiariaService.execute(filter, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = { MediaType.APPLICATION_PDF_VALUE })
    @Override
    public ResponseEntity<byte[]> vendaDiariasPdf(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] vendasDiariasReport = vendaReportService.execute(filter, timeOffset);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas_diarias.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return ResponseEntity.ok()
            .headers(headers)
            .body(vendasDiariasReport);
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> { }

}
