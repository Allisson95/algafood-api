package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Transactional
    @Override
    public void remove(FotoProduto foto) {
        manager.remove(foto);
    }

}
