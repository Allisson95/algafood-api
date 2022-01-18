package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler implements Assembler<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		return mapper.map(restaurante, RestauranteModel.class);
	}

}
