package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PedidoConfirmadoEvent {

    private final Pedido pedido;

}
