package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GrupoModel {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Gerente")
	private String nome;

}
