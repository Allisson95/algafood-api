package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CadastroCozinha {

	@PersistenceContext
	private EntityManager manager;

	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}

	public List<Cozinha> listar() {
		return manager.createQuery("FROM Cozinha", Cozinha.class).getResultList();
	}

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
}
