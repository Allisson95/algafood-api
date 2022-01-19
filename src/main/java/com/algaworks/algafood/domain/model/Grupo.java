package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@ManyToMany
	@JoinTable(name = "grupo_permissao",
		joinColumns = { @JoinColumn(name = "grupo_id") },
		inverseJoinColumns = { @JoinColumn(name = "permissao_id") })
	private Set<Permissao> permissoes = new HashSet<>(0);

	public boolean adicionarPermissao(Permissao permissao) {
		return permissoes.add(permissao);
	}

	public boolean removerPermissao(Permissao permissao) {
		return permissoes.removeIf(p -> p.getId().equals(permissao.getId()));
	}

}
