package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.core.validation.Groups;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column
	private String nome;

	@NotNull
	@PositiveOrZero
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class )
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cozinha_id")
	private Cozinha cozinha;

	@Embedded
	private Endereco endereco;

	@Column
	private Boolean ativo = Boolean.TRUE;

	@Column
	private Boolean aberto = Boolean.FALSE;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
		joinColumns = { @JoinColumn(name = "restaurante_id") },
		inverseJoinColumns = { @JoinColumn(name = "forma_pagamento_id") })
	private Set<FormaPagamento> formasPagamento = new HashSet<>(0);

	@OneToMany(mappedBy = "restaurante")
	private Set<Produto> produtos = new HashSet<>(0);

	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
		joinColumns = { @JoinColumn(name = "restaurante_id") },
		inverseJoinColumns = { @JoinColumn(name = "usuario_id") })
	private Set<Usuario> responsaveis = new HashSet<>(0);

	public void ativar() {
		setAtivo(true);
	}

	public void desativar() {
		setAtivo(false);
	}

	public void abrir() {
		setAberto(true);
	}

	public void fechar() {
		setAberto(false);
	}

    public boolean podeAtivar() {
        return getAtivo() == Boolean.FALSE;
    }

    public boolean podeDesativar() {
        return getAtivo() == Boolean.TRUE;
    }

    public boolean podeAbrir() {
        return getAtivo() == Boolean.TRUE && getAberto() == Boolean.FALSE;
    }

    public boolean podeFechar() {
        return getAberto() == Boolean.TRUE;
    }

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().removeIf(fp -> fp.equals(formaPagamento));
	}

	public boolean adicionarResponsavel(Usuario responsavel) {
		return getResponsaveis().add(responsavel);
	}

	public boolean removerResponsavel(Usuario responsavel) {
		return getResponsaveis().removeIf(fp -> fp.equals(responsavel));
	}

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().stream().anyMatch(fp -> fp.equals(formaPagamento));
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

}
