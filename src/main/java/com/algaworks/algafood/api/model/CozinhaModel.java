package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CozinhaModel {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Brasileira")
	private String nome;

}
