package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteResponsavel;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteResponsavelAssociar;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteResponsavelDesassociar;

import java.util.Set;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    @Override
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
        Set<Usuario> responsaveis = restaurante.getResponsaveis();

        CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler.toCollectionModel(responsaveis)
                .mapLink(
                        IanaLinkRelations.SELF,
                        actualSelfLink -> linkToRestauranteResponsavel(restaurante.getId(), actualSelfLink.getRel()))
                .add(linkToRestauranteResponsavelAssociar(restaurante.getId(), LinkRelation.of("associar")));

        usuariosModel.getContent()
                .forEach(usuarioModel ->
                    usuarioModel.add(linkToRestauranteResponsavelDesassociar(
                            restaurante.getId(),
                            usuarioModel.getId(),
                            LinkRelation.of("desassociar"))));

        return usuariosModel;
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.adicionarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.removerResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
