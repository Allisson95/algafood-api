package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToCozinha;
import static com.algaworks.algafood.api.AlgaLinks.linkToCozinhas;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper mapper;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha entity) {
        CozinhaModel cozinhaModel = mapper.map(entity, CozinhaModel.class);

        cozinhaModel.add(linkToCozinha(entity.getId()));
        cozinhaModel.add(linkToCozinhas());

        return cozinhaModel;
    }

}
