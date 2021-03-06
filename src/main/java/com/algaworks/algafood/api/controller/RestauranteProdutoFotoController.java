package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.domain.service.FileStorageService.RecoveredFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FileStorageService fileStorage;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Override
    public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoSalva = catalogoFotoProduto.buscar(restauranteId, produtoId);
        return fotoProdutoModelAssembler.toModel(fotoSalva);
    }

    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @Override
    public ResponseEntity<?> buscarImagem(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestHeader(name = HttpHeaders.ACCEPT) String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException, URISyntaxException {
        FotoProduto fotoSalva = catalogoFotoProduto.buscar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoSalva.getContentType());
        List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
        
        boolean naoCompativel = mediaTypesAceitas.stream()
        .noneMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
        if (naoCompativel) {
            throw new HttpMediaTypeNotAcceptableException(List.of(mediaTypeFoto));
        }

        RecoveredFile fotoRecuperada = fileStorage.get(fotoSalva.getNome());

        if (fotoRecuperada.hasUrl()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(fotoRecuperada.getUrl().toURI())
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getContent()));
        }
    }

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Override
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput input) throws IOException {
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);

        MultipartFile arquivo = input.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(input.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNome(arquivo.getOriginalFilename());

        foto = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(foto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProduto.remover(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}
