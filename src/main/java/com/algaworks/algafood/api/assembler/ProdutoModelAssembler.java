package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToProdutoFoto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProdutos;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper mapper;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = mapper.map(produto, ProdutoModel.class);

        produtoModel.add(linkToProduto(produto.getRestaurante().getId(), produto.getId()));
        produtoModel.add(linkToProdutos(produto.getRestaurante().getId(), IanaLinkRelations.COLLECTION));
        produtoModel.add(linkToProdutoFoto(produto.getRestaurante().getId(), produto.getId(), LinkRelation.of("foto")));

        return produtoModel;
    }

}
