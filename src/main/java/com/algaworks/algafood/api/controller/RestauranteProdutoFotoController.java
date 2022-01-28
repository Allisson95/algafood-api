package com.algaworks.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput input) {
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);

        MultipartFile arquivo = input.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(input.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNome(arquivo.getOriginalFilename());

        foto = catalogoFotoProduto.salvar(foto);

        return fotoProdutoModelAssembler.toModel(foto);
    }

}
