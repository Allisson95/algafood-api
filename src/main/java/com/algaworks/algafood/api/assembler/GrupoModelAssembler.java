package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler implements Assembler<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public GrupoModel toModel(Grupo source) {
		return mapper.map(source, GrupoModel.class);
	}

}
