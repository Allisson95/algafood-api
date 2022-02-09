package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Relation(collectionRelation = "permissoes")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "CONSULTAR_COZINHAS")
    private String nome;

    @Schema(example = "Permite consultar cozinhas")
    private String descricao;

}
