package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Relation(collectionRelation = "cozinhas")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Brasileira")
	private String nome;

}
