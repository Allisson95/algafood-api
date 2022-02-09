package com.algaworks.algafood.api.openapi.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.PRODUTOS)
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Busca a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary")),
            }),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    FotoProdutoModel buscar(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<?> buscarImagem(
            Long restauranteId,
            Long produtoId,
            String acceptHeader) throws HttpMediaTypeNotAcceptableException, URISyntaxException;

    @Operation(summary = "Atualiza a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    FotoProdutoModel atualizarFoto(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
            @RequestBody(required = true) FotoProdutoInput input) throws IOException;

    @Operation(summary = "Exclui a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    ResponseEntity<Void> remover(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

}
