package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler implements Assembler<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public CidadeModel toModel(Cidade source) {
		return mapper.map(source, CidadeModel.class);
	}

}
