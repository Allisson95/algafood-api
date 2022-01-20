package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @Min(1)
    @NotNull
    private Integer quantidade;

    private String observacao;

}
