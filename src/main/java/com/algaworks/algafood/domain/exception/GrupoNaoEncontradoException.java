package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe cadastro de grupo com código %d";

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long grupoId) {
		super(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
	}

}
