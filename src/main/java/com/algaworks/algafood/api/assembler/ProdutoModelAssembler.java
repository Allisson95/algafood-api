package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler implements Assembler<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProdutoModel toModel(Produto source) {
        return mapper.map(source, ProdutoModel.class);
    }

}
