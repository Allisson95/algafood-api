package com.algaworks.algafood.infrastructure.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaService;
import com.algaworks.algafood.domain.service.VendaReportService;
import com.algaworks.algafood.infrastructure.exception.ReportException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperVendaReportService implements VendaReportService {

    @Autowired
    private VendaDiariaService vendaDiariaService;

    @Override
    public byte[] execute(VendaDiariaFilter filter, String timeOffset) {
        try {
            InputStream reportStream = this.getClass().getResourceAsStream("/reports/vendas_diarias.jasper");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            List<VendaDiaria> vendasDiarias = vendaDiariaService.execute(filter, timeOffset);
            JRDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }

}
