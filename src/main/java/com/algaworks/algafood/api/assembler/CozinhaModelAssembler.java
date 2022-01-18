package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler implements Assembler<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public CozinhaModel toModel(Cozinha source) {
		return mapper.map(source, CozinhaModel.class);
	}

}
