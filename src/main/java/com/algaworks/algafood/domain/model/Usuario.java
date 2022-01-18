package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column
	private String email;

	@Column
	private String senha;

	@CreationTimestamp
	@Column(name = "data_cadastro", columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@ManyToMany
	@JoinTable(name = "usuario_grupo",
		joinColumns = { @JoinColumn(name = "usuario_id") },
		inverseJoinColumns = { @JoinColumn(name = "grupo_id") })
	private List<Grupo> grupos = new ArrayList<>(0);

	public void alterarSenha(String senhaAtual, String novaSenha) {
		if (!senhaAtual.equals(getSenha())) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}

		setSenha(novaSenha);
	}

}
