package com.algaworks.algafood.api.model.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomain(RestauranteInput input) {
		Restaurante restaurante = new Restaurante();

		Cozinha cozinha = new Cozinha();
		cozinha.setId(input.getCozinha().getId());

		restaurante.setNome(input.getNome());
		restaurante.setTaxaFrete(input.getTaxaFrete());
		restaurante.setCozinha(cozinha);

		return restaurante;
	}

}
