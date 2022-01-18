package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler implements Assembler<Estado, EstadoModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public EstadoModel toModel(Estado source) {
		return mapper.map(source, EstadoModel.class);
	}

}
