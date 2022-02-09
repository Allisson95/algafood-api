package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProdutoFoto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProdutoFotoRemover;

import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper mapper;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = mapper.map(fotoProduto, FotoProdutoModel.class);
        Produto produto = fotoProduto.getProduto();

        fotoProdutoModel.add(linkToProdutoFoto(produto.getRestaurante().getId(), produto.getId()));
        fotoProdutoModel.add(linkToProduto(produto.getRestaurante().getId(), produto.getId(), LinkRelation.of("produto")));
        fotoProdutoModel.add(linkToProdutoFotoRemover(produto.getRestaurante().getId(), produto.getId(), LinkRelation.of("remover")));

        return fotoProdutoModel;
    }

}
