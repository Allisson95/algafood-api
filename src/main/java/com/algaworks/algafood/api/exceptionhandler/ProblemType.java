package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada");

	private final String title;
	private final String uri;

	private ProblemType(final String title, final String path) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
