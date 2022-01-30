package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

	private static final String MSG_FOTO_PRODUTO_NAO_ENCONTRADO = "Não existe cadastro de foto de produto com código %d para o restaurante de código %d";

    public FotoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format(MSG_FOTO_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
    }

}
