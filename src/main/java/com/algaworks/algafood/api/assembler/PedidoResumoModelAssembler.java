package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToPedidos;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurante;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuario;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper mapper;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido entity) {
        PedidoResumoModel pedidoModel = mapper.map(entity, PedidoResumoModel.class);

        pedidoModel.add(linkToPedido(entity.getCodigo()));
        pedidoModel.add(linkToPedidos());
        pedidoModel.getRestaurante().add(linkToRestaurante(entity.getRestaurante().getId()));
        pedidoModel.getCliente().add(linkToUsuario(entity.getCliente().getId()));

        return pedidoModel;
    }

}
