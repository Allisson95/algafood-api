package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.ItemPedido;

import org.modelmapper.PropertyMap;

public class ItemPedidoInputMap extends PropertyMap<ItemPedidoInput, ItemPedido> {

    @Override
    protected void configure() {
        skip().setId(null);
        skip().setPedido(null);
    }

}
