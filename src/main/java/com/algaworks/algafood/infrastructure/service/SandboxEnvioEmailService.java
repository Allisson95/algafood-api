package com.algaworks.algafood.infrastructure.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.algaworks.algafood.core.email.EmailProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage criarMimeMessage(Email email) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(email);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, !email.getAnexos().isEmpty(), "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());

        return mimeMessage;
    }

}
