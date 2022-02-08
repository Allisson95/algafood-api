package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.hateoas.CollectionModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.CIDADES)
public interface CidadeControllerOpenApi {

        @Operation(summary = "Lista as cidades")
        CollectionModel<CidadeModel> listar();

        @Operation(summary = "Busca uma cidade por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = {
                                        @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {
                                        @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
        })
        CidadeModel buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

        @Operation(summary = "Cadastra uma cidade", responses = {
                        @ApiResponse(responseCode = "201", description = "Cidade cadastrada")
        })
        CidadeModel adicionar(
                        @RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

        @Operation(summary = "Atualiza uma cidade por ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {
                                        @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
        })
        CidadeModel atualizar(
                        @Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId,
                        @RequestBody(description = "Representação de uma cidade com os novos dados", required = true) CidadeInput cidadeInput);

        @Operation(summary = "Exclui uma cidade por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "Cidade excluída"),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {
                                        @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
        })
        void remover(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}
