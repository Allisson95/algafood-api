package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler implements Disassembler<RestauranteInput, Restaurante> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Restaurante toDomain(RestauranteInput input) {
		return mapper.map(input, Restaurante.class);
	}

	@Override
	public void copyToDomain(RestauranteInput input, Restaurante restaurante) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Cozinha was altered from 3 to 1
		restaurante.setCozinha(new Cozinha());

		mapper.map(input, restaurante);
	}

}
