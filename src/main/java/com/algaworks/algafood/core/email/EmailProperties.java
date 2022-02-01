package com.algaworks.algafood.core.email;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    private Impl impl = Impl.FAKE;
    private Sandbox sandbox = new Sandbox();

    @NotNull
    private String remetente;

    public enum Impl {
        FAKE, SMTP, SANDBOX;
    }

    @Getter
    @Setter
    public static class Sandbox {

        private String destinatario;

    }

}
