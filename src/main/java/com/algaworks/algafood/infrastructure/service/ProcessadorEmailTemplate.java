package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Modelo;
import com.algaworks.algafood.infrastructure.exception.EmailException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freemarker;

    public String processarTemplate(Modelo modelo) {
        try {
            Template template = freemarker.getTemplate(modelo.getNome());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelo.getParametros());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }

}
