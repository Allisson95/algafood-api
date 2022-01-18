package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormaPagamentoInput {

	@NotBlank
	private String descricao;

}