package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnderecoInput {

	@Schema(example = "38400-000")
	@NotBlank
	private String cep;

	@Schema(example = "Rua Floriano Peixoto")
	@NotBlank
	private String logradouro;

	@Schema(example = "\"1500\"")
	@NotBlank
	private String numero;

	@Schema(example = "Apto 901")
	private String complemento;

	@Schema(example = "Centro")
	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
