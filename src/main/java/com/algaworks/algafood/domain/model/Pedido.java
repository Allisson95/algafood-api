package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "subtotal")
	private BigDecimal subtotal;

	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@CreationTimestamp
	@Column(name = "data_criacao")
	private OffsetDateTime dataCriacao;

	@Column(name = "data_confirmacao")
	private OffsetDateTime dataConfirmacao;

	@Column(name = "data_entrega")
	private OffsetDateTime dataEntrega;

	@Column(name = "data_cancelamento")
	private OffsetDateTime dataCancelamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusPedido status = StatusPedido.CRIADO;

	@Embedded
	private Endereco enderecoEntrega;

	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id")
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id")
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>(0);

}
