package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EstadoIdInput {

	@NotNull
	private Long id;

}