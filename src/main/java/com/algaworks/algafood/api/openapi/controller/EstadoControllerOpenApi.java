package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.ESTADOS)
public interface EstadoControllerOpenApi {

	@Operation(summary = "Lista os estados")
	List<EstadoModel> listar();

	@Operation(summary = "Busca um estado por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do estado inválido", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	EstadoModel buscar(@Parameter(description = "ID de um estado", example = "1", required = true) Long estadoId);

	@Operation(summary = "Cadastra um estado", responses = {
			@ApiResponse(responseCode = "201", description = "Estado cadastrado")
	})
	EstadoModel adicionar(
			@RequestBody(description = "Representação de um novo estado", required = true) EstadoInput estadoInput);

	@Operation(summary = "Atualiza uma cidade por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Estado atualizado"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	EstadoModel atualizar(
			@Parameter(description = "ID de um estado", example = "1", required = true) Long estadoId,
			@RequestBody(description = "Representação de um estado com os novos dados", required = true) EstadoInput estadoInput);

	@Operation(summary = "Exclui um estado por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Estado excluído"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	void remover(@Parameter(description = "ID de um estado", example = "1", required = true) Long estadoId);

}
