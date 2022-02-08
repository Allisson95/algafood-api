package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.SmtpEnvioEmailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    public EnvioEmailService envioEmailService(EmailProperties emailProperties) {
        return switch (emailProperties.getImpl()) {
            case SMTP -> new SmtpEnvioEmailService();
            case SANDBOX -> new SandboxEnvioEmailService();
            default -> new FakeEnvioEmailService();
        };
    }

}
