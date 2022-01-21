package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);

	private final String descricao;
	private final List<StatusPedido> statusAnterioresPermitidos;

	private StatusPedido(String descricao, StatusPedido... statusAnterioresPermitidos) {
		this.descricao = descricao;
		this.statusAnterioresPermitidos = Arrays.asList(statusAnterioresPermitidos);
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean podeAlterarPara(StatusPedido novoStatus) {
		return novoStatus.statusAnterioresPermitidos.contains(this);
	}

	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !podeAlterarPara(novoStatus);
	}

}
