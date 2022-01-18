package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe cadastro de forma de pagamento com código %d";

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
		super(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, formaPagamentoId));
	}

}
