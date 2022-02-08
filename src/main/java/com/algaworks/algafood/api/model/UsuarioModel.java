package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Relation(collectionRelation = "usuarios")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "João da Silva")
	private String nome;

	@Schema(example = "joao.ger@algafood.com.br")
	private String email;

}
