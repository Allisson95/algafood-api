package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RestauranteModel {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Thai Gourmet")
	private String nome;

	@Schema(example = "12.00")
	private BigDecimal taxaFrete;

	private CozinhaModel cozinha;
	private EnderecoModel endereco;
	private Boolean ativo;
	private Boolean aberto;

}
