package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsuarioInput {

	@Schema(example = "João da Silva")
	@NotBlank
	private String nome;

	@Schema(example = "joao.ger@algafood.com.br")
	@NotBlank
	@Email
	private String email;

}
