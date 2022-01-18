package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnderecoInput {

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
