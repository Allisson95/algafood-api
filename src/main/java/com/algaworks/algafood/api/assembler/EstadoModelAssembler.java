package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstados;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper mapper;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado entity) {
        EstadoModel estadoModel = mapper.map(entity, EstadoModel.class);

        estadoModel.add(linkToEstado(entity.getId()));
        estadoModel.add(linkToEstados());

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkToEstados(IanaLinkRelations.SELF));
    }

}
