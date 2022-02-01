package com.algaworks.algafood.infrastructure.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Anexo;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Modelo;
import com.algaworks.algafood.infrastructure.exception.EmailException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarker;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Email email) {
        try {
            MimeMessage mimeMessage = criarMimeMessage(email);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail", e);
        }
    }

    protected MimeMessage criarMimeMessage(Email email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, email.getAnexos().isEmpty(), "UTF-8");
        helper.setFrom(emailProperties.getRemetente());
        helper.setTo(email.getDestinatarios().toArray(String[]::new));
        helper.setSentDate(new Date());
        helper.setSubject(email.getAssunto());

        String text = processarTemplate(email.getModelo());
        helper.setText(text, true);

        for (Anexo anexo : email.getAnexos()) {
            helper.addAttachment(
                    "Anexo: " + anexo.getNome(),
                    new InputStreamResource(anexo.getConteudo()),
                    anexo.getContentType());
        }

        return mimeMessage;
    }

    protected String processarTemplate(Modelo modelo) {
        try {
            Template template = freemarker.getTemplate(modelo.getNome());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelo.getParametros());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }

}
