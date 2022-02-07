package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.core.openapi.AlgaFoodTags;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = AlgaFoodTags.ESTATISTICAS)
public interface EstatisticaControllerOpenApi {

    @Operation(
        summary = "Consulta estatísticas de vendas diárias",
        parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "restauranteId", description = "ID do restaurante", example = "1", schema = @Schema(type = "integer")),
			@Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio", description = "Data/hora inicial da criação do pedido", example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
			@Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim", description = "Data/hora final da criação do pedido", example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
        },
        responses = {
            @ApiResponse(responseCode = "200", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
                @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")),
            }),
        }
    )
    List<VendaDiaria> vendaDiarias(
        @Parameter(hidden = true) VendaDiariaFilter filter,
        @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", schema = @Schema(type = "string", defaultValue = "+00:00")) String timeOffset
    );

    @Operation(hidden = true)
    ResponseEntity<byte[]> vendaDiariasPdf(VendaDiariaFilter filter, String timeOffset);

}
