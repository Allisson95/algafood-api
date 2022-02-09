package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.hateoas.CollectionModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.PERMISSOES)
public interface PermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões associadas a um grupo")
    CollectionModel<PermissaoModel> listar();

}
