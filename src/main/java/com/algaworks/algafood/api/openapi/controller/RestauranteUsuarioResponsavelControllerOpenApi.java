package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.hateoas.CollectionModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.RESTAURANTES)
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @Operation(summary = "Lista os usuários responsáveis associados a restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    CollectionModel<UsuarioModel> listar(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Associação de restaurante com usuário responsável", responses = {
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    void associar(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

    @Operation(summary = "Desassociação de restaurante com usuário responsável", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    void desassociar(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

}
