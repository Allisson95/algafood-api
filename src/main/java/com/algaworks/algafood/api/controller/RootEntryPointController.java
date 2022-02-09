package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.api.AlgaLinks.linkToCidades;
import static com.algaworks.algafood.api.AlgaLinks.linkToCozinhas;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstados;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstatisticas;
import static com.algaworks.algafood.api.AlgaLinks.linkToFormasPagamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToGrupos;
import static com.algaworks.algafood.api.AlgaLinks.linkToPermissoes;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurantes;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarios;

import java.util.concurrent.TimeUnit;

import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping
public class RootEntryPointController {

    @Operation(hidden = true)
    @GetMapping
    public ResponseEntity<RootEntryPointModel> root() {
        RootEntryPointModel root = new RootEntryPointModel();

        root.add(
                linkToEstados(LinkRelation.of("estados")),
                linkToCidades(LinkRelation.of("cidades")),
                linkToCozinhas(LinkRelation.of("cozinhas")),
                linkToRestaurantes(LinkRelation.of("restaurantes")),
                linkToFormasPagamento(LinkRelation.of("formas-pagamento")),
                linkToUsuarios(LinkRelation.of("usuarios")),
                linkToPermissoes(LinkRelation.of("permissoes")),
                linkToGrupos(LinkRelation.of("grupos")),
                linkToEstatisticas(LinkRelation.of("estatisticas"))
            );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS).cachePublic())
                .body(root);
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> { }

}
