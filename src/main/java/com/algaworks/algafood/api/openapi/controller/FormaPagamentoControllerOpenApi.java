package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.FORMAS_PAGAMENTO)
public interface FormaPagamentoControllerOpenApi {

	@Operation(summary = "Lista as formas de pagamento")
	ResponseEntity<List<FormaPagamentoModel>> listar();

	@Operation(summary = "Busca uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<FormaPagamentoModel> buscar(
			@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@Operation(summary = "Cadastra uma forma de pagamento", responses = {
			@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"),
	})
	FormaPagamentoModel adicionar(@RequestBody(description = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

	@Operation(summary = "Atualiza uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	FormaPagamentoModel atualizar(
		@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
		@RequestBody(description = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoInput formaPagamentoInput);

	@Operation(summary = "Exclui uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) })
	})
	void remover(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
