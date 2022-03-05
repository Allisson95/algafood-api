package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.service.EnvioEmailService;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private ProcessadorEmailTemplate processadorEmailTemplate;

    @Override
    public void enviar(Email email) {
        String text = processadorEmailTemplate.processarTemplate(email.getModelo());

        log.info("[FAKE E-MAIL] para {}\n{}", email.getDestinatarios(), text);
    }

}
