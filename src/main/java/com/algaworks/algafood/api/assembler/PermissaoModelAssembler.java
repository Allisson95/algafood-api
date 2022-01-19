package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler implements Assembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public PermissaoModel toModel(Permissao source) {
        return mapper.map(source, PermissaoModel.class);
    }

}
