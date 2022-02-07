package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NovaSenhaInput {

	@Schema(example = "123", type = "string", format = "password")
	@NotBlank
	private String senhaAtual;

	@Schema(example = "123", type = "string", format = "password")
	@NotBlank
	private String novaSenha;

}
