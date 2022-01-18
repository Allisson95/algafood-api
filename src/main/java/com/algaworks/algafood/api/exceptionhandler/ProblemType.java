package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
	ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
	PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
	ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
	DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos");

	private final String title;
	private final String uri;

	private ProblemType(final String title, final String path) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
