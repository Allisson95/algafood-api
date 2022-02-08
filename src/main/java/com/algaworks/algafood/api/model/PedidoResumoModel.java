package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Relation(collectionRelation = "pedidos")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    @Schema(example = "04813f77-79b5-11ec-9a17-0242ac1b0002")
    private UUID codigo;

    @Schema(example = "298.90")
    private BigDecimal subtotal;

    @Schema(example = "10.00")
    private BigDecimal taxaFrete;

    @Schema(example = "308.90")
    private BigDecimal valorTotal;

    @Schema(example = "CRIADO")
    private String status;

    @Schema(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;

}
