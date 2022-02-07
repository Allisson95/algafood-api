package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnderecoModel {

	@Schema(example = "38400-000")
	private String cep;

	@Schema(example = "Rua Floriano Peixoto")
	private String logradouro;

	@Schema(example = "\"1500\"")
	private String numero;

	@Schema(example = "Apto 901")
	private String complemento;

	@Schema(example = "Centro")
	private String bairro;

	private CidadeModel cidade;

}
