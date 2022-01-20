package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler implements Assembler<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public PedidoResumoModel toModel(Pedido source) {
        return mapper.map(source, PedidoResumoModel.class);
    }

}
