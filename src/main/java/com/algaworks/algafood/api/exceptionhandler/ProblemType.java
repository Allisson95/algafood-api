package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
	ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel");

	private final String title;
	private final String uri;

	private ProblemType(final String title, final String path) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
