package com.algaworks.algafood.api.openapi.controller;

import java.util.UUID;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.model.PagePedidoResumoModel;
import com.algaworks.algafood.core.openapi.AlgaFoodTags;
import com.algaworks.algafood.core.openapi.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.PEDIDOS)
public interface PedidoControllerOpenApi {

    @Operation(
            summary = "Pesquisa os pedidos",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "clienteId", description = "ID do cliente para filtro da pesquisa", example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = ParameterIn.QUERY, name = "restauranteId", description = "ID do restaurante para filtro da pesquisa", example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio", description = "Data/hora de criação inicial para filtro da pesquisa", example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                    @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim", description = "Data/hora de criação final para filtro da pesquisa", example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = PagePedidoResumoModel.class)),
                    }),
            }
    )
    @PageableParameter
    Page<PedidoResumoModel> pesquisar(
            @Parameter(hidden = true) PedidoFilter filter,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca um pedido por código", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    PedidoModel buscar(
            @Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002", required = true) UUID codigoPedido);

    @Operation(summary = "Registra um pedido", responses = {
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoModel emitir(
            @RequestBody(description = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);

    @Operation(summary = "Confirmação de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    void confirmar(
            @Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002", required = true) UUID codigoPedido);

    @Operation(summary = "Registrar entrega de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    void entregar(
            @Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002", required = true) UUID codigoPedido);

    @Operation(summary = "Cancelamento de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(mediaType = "application/problem+json", schema = @Schema(ref = "Problema")) }),
    })
    void cancelar(
            @Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002", required = true) UUID codigoPedido);

}
