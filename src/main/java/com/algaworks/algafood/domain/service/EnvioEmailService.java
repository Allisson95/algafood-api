package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

    void enviar(Email email);

    @Builder
    @Getter
    class Email {

        @NonNull
        private String assunto;
        @NonNull
        private Modelo modelo;
        @Singular
        private Set<String> destinatarios;
        @Singular
        private List<Anexo> anexos;

        @Builder
        @Getter
        public static class Modelo {

            @NonNull
            private String nome;
            @Singular
            private Map<String, Object> parametros;

        }

        @Builder
        @Getter
        public static class Anexo {

            @NonNull
            private String nome;
            @NonNull
            private InputStream conteudo;
            @NonNull
            private String contentType;

        }

    }

}
