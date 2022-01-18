package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Problema {

	private LocalDateTime dataHora;
	private String mensagem;

}
