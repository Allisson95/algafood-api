package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper mapper;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido entity) {
        PedidoModel pedidoModel = mapper.map(entity, PedidoModel.class);

        pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedidoModel.getCodigo())).withSelfRel());
        pedidoModel.add(linkTo(methodOn(PedidoController.class).pesquisar(null, null)).withRel("pedidos"));

        pedidoModel.getRestaurante()
                .add(linkTo(methodOn(RestauranteController.class).buscar(pedidoModel.getRestaurante().getId()))
                        .withSelfRel());

        pedidoModel.getCliente()
                .add(linkTo(methodOn(UsuarioController.class).buscar(pedidoModel.getCliente().getId())).withSelfRel());

        pedidoModel.getFormaPagamento()
                .add(linkTo(methodOn(FormaPagamentoController.class).buscar(pedidoModel.getFormaPagamento().getId()))
                        .withSelfRel());

        pedidoModel.getEnderecoEntrega().getCidade().add(
                linkTo(methodOn(CidadeController.class).buscar(pedidoModel.getEnderecoEntrega().getCidade().getId()))
                        .withSelfRel());

        pedidoModel.getEnderecoEntrega().getCidade().getEstado().add(
                linkTo(methodOn(EstadoController.class)
                        .buscar(pedidoModel.getEnderecoEntrega().getCidade().getEstado().getId()))
                                .withSelfRel());

        pedidoModel.getItens().forEach(itemPedido -> {
            itemPedido.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoModel.getRestaurante().getId(), itemPedido.getProdutoId())).withRel("produto"));
        });

        return pedidoModel;
    }

}
