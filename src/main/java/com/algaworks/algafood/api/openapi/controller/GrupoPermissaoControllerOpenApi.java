package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.GRUPOS)
public interface GrupoPermissaoControllerOpenApi {

	@Operation(summary = "Lista as permissões associadas a um grupo", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
	})
	CollectionModel<PermissaoModel> buscar(
			@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId);

	@Operation(summary = "Associação de permissão com grupo", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> associar(
			@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId,
			@Parameter(description = "ID de uma permissão", example = "1", required = true) Long permissaoId);

	@Operation(summary = "Desassociação de permissão com grupo", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> desassociar(
			@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId,
			@Parameter(description = "ID de uma permissão", example = "1", required = true) Long permissaoId);

}
