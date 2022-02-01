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
        switch (emailProperties.getImpl()) {
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return new FakeEnvioEmailService();
        }
    }

}
