package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Relation(collectionRelation = "fotos")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    private String nome;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
