package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;

	@Column(name = "preco_total")
	private BigDecimal precoTotal;

	@Column(name = "observacao")
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	public void calcularValorTotal() {
		BigDecimal valorUnitario = Optional.ofNullable(getPrecoUnitario()).orElse(BigDecimal.ZERO);
		Integer qtd = Optional.ofNullable(getQuantidade()).orElse(0);

		BigDecimal valorTotal = valorUnitario.multiply(BigDecimal.valueOf(qtd.longValue()));

		setPrecoTotal(valorTotal);
	}

}
