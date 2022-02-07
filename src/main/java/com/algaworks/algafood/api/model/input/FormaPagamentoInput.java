package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormaPagamentoInput {

	@Schema(example = "Cartão de crédito")
	@NotBlank
	private String descricao;

}
