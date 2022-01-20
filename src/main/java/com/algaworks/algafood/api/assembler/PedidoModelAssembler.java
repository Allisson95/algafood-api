package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements Assembler<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public PedidoModel toModel(Pedido source) {
        return mapper.map(source, PedidoModel.class);
    }

}
