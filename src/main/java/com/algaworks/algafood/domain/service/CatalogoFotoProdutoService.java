package com.algaworks.algafood.domain.service;

import java.io.InputStream;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FileStorageService.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FileStorageService fileStorage;

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream arquivo) {
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        String novoNome = fileStorage.generateRandomName(foto.getNome());

        String nomeFotoExistente = null;
        try {
            FotoProduto fotoExistente = buscar(restauranteId, produtoId);
            produtoRepository.remove(fotoExistente);
            nomeFotoExistente = fotoExistente.getNome();
        } catch (FotoProdutoNaoEncontradoException e) {
            // Não existe foto cadastrada para substituição, então cadastra uma nova.
        }

        foto.setNome(novoNome);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        File file = File.builder()
                .name(novoNome)
                .content(arquivo)
                .build();

        fileStorage.replace(file, nomeFotoExistente);

        return foto;
    }

    @Transactional
    public void remover(Long restauranteId, Long produtoId) {
        FotoProduto fotoSalva = buscar(restauranteId, produtoId);

        produtoRepository.remove(fotoSalva);
        produtoRepository.flush();

        fileStorage.remove(fotoSalva.getNome());
    }

}
