package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FotoProdutoModel {

    private String nome;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
