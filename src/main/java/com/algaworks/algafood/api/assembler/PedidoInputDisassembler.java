package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler implements Disassembler<PedidoInput, Pedido> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public Pedido toDomain(PedidoInput input) {
        return mapper.map(input, Pedido.class);
    }

    @Override
    public void copyToDomain(PedidoInput input, Pedido domain) {
        mapper.map(input, domain);
    }

}
