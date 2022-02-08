package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
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

        pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedidoModel.getCodigo())).withSelfRel());
        pedidoModel.add(linkTo(methodOn(PedidoController.class).pesquisar(null, null)).withRel("pedidos"));

        pedidoModel.getRestaurante()
                .add(linkTo(methodOn(RestauranteController.class).buscar(pedidoModel.getRestaurante().getId()))
                        .withSelfRel());

        pedidoModel.getCliente()
                .add(linkTo(methodOn(UsuarioController.class).buscar(pedidoModel.getCliente().getId())).withSelfRel());

        return pedidoModel;
    }

}
