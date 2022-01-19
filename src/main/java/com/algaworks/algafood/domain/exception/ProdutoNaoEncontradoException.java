package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(
                String.format("Não existe cadastro de produto com código %d para o restaurante de código %d",
                        produtoId,
                        restauranteId));
    }

}
