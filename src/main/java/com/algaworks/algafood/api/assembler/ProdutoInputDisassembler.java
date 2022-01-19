package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler implements Disassembler<ProdutoInput, Produto> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public Produto toDomain(ProdutoInput input) {
        return mapper.map(input, Produto.class);
    }

    @Override
    public void copyToDomain(ProdutoInput input, Produto domain) {
        mapper.map(input, domain);
    }

}
