package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public final class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

	private CidadeMixin() {}

}
