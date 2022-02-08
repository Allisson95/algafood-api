package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToCidade;
import static com.algaworks.algafood.api.AlgaLinks.linkToCidades;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper mapper;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade entity) {
        CidadeModel cidadeModel = mapper.map(entity, CidadeModel.class);

        cidadeModel.add(linkToCidade(entity.getId()));
        cidadeModel.add(linkToCidades());
        cidadeModel.getEstado().add(linkToEstado(entity.getEstado().getId()));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkToCidades());
    }

}
