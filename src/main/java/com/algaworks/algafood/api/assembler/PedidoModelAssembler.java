package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToCancelamentoPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToCidade;
import static com.algaworks.algafood.api.AlgaLinks.linkToCofirmacaoPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToEntragaPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;
import static com.algaworks.algafood.api.AlgaLinks.linkToFormaPagamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToPedidos;
import static com.algaworks.algafood.api.AlgaLinks.linkToProdutos;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurante;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuario;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.LinkRelation;
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

        pedidoModel.add(linkToPedido(entity.getCodigo()));
        pedidoModel.add(linkToPedidos(LinkRelation.of("pedidos")));
        pedidoModel.getRestaurante().add(linkToRestaurante(entity.getRestaurante().getId()));
        pedidoModel.getCliente().add(linkToUsuario(entity.getCliente().getId()));
        pedidoModel.getFormaPagamento().add(linkToFormaPagamento(entity.getFormaPagamento().getId()));
        pedidoModel.getEnderecoEntrega().getCidade().add(linkToCidade(entity.getEnderecoEntrega().getCidade().getId()));
        pedidoModel.getEnderecoEntrega().getCidade().getEstado()
                .add(linkToEstado(entity.getEnderecoEntrega().getCidade().getEstado().getId()));

        pedidoModel.getItens().forEach(itemPedido -> itemPedido.add(
                linkToProdutos(entity.getRestaurante().getId(), itemPedido.getProdutoId(), LinkRelation.of("produto"))));

        if (entity.podeSerConfirmado()) {
            pedidoModel.add(linkToCofirmacaoPedido(entity.getCodigo(), LinkRelation.of("confirmar")));
        }

        if (entity.podeSerEntregue()) {
            pedidoModel.add(linkToEntragaPedido(entity.getCodigo(), LinkRelation.of("entregar")));
        }

        if (entity.podeSerCancelado()) {
            pedidoModel.add(linkToCancelamentoPedido(entity.getCodigo(), LinkRelation.of("cancelar")));
        }

        return pedidoModel;
    }
}
