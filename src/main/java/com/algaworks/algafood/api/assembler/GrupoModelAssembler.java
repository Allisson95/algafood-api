package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToGrupo;
import static com.algaworks.algafood.api.AlgaLinks.linkToGrupoPermissoes;
import static com.algaworks.algafood.api.AlgaLinks.linkToGrupos;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper mapper;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = mapper.map(grupo, GrupoModel.class);

        grupoModel.add(linkToGrupo(grupo.getId()));
        grupoModel.add(linkToGrupos());
        grupoModel.add(linkToGrupoPermissoes(grupo.getId(), LinkRelation.of("permissoes")));

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(linkToGrupos(IanaLinkRelations.SELF));
    }

}
