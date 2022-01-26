package com.algaworks.algafood.domain.exception;

import java.util.UUID;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe cadastro de pedido com código %s";

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(UUID codigoPedido) {
        this(String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigoPedido.toString()));
    }

}
