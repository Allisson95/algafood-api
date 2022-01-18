package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CidadeModel {

	private Long id;
	private String nome;
	private EstadoModel estado;

}
