package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.domain.model.FotoProduto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler implements Assembler<FotoProduto, FotoProdutoModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public FotoProdutoModel toModel(FotoProduto source) {
		return mapper.map(source, FotoProdutoModel.class);
	}

}
