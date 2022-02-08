package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EstadoModel extends RepresentationModel<EstadoModel> {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Minas Gerais")
	private String nome;

}
