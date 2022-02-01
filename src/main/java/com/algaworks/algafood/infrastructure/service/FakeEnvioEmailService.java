package com.algaworks.algafood.infrastructure.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Email email) {
        String text = processarTemplate(email.getModelo());

        log.info("[FAKE E-MAIL] para {}\n{}", email.getDestinatarios(), text);
    }

}
