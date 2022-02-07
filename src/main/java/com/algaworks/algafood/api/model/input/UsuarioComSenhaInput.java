package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class UsuarioComSenhaInput extends UsuarioInput {

	@Schema(example = "123", type = "string", format = "password")
	@NotBlank
	private String senha;

}
