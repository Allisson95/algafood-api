package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPermissoes;

import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper mapper;

    public PermissaoModelAssembler() {
        super(PermissaoController.class, PermissaoModel.class);
    }

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        return mapper.map(permissao, PermissaoModel.class);
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities)
                .add(linkToPermissoes(IanaLinkRelations.SELF));
    }

}
