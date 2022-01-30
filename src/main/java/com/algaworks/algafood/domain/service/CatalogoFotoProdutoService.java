package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

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

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream arquivo) {
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Long produtoId = foto.getProduto().getId();
        String novoNome = fileStorage.generateRandomName(foto.getNome());
        String nomeFotoExistente = null;

        Optional<FotoProduto> fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoProduto.isPresent()) {
            produtoRepository.remove(fotoProduto.get());
            nomeFotoExistente = fotoProduto.get().getNome();
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

}
