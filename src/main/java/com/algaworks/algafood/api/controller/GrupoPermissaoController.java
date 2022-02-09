package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.api.AlgaLinks.linkToGrupoPermissaoAssociar;
import static com.algaworks.algafood.api.AlgaLinks.linkToGrupoPermissaoDesassociar;
import static com.algaworks.algafood.api.AlgaLinks.linkToGrupoPermissoes;

import java.util.Set;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    @Override
    public CollectionModel<PermissaoModel> buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        Set<Permissao> permissoes = grupo.getPermissoes();
        CollectionModel<PermissaoModel> permissoesModel = permissaoModelAssembler.toCollectionModel(permissoes)
                .mapLink(
                        IanaLinkRelations.SELF,
                        actualSelfLink -> linkToGrupoPermissoes(grupo.getId(), actualSelfLink.getRel()))
                .add(linkToGrupoPermissaoAssociar(grupo.getId(), LinkRelation.of("associar")));

        permissoesModel.getContent().forEach(permissaoModel -> permissaoModel.add(linkToGrupoPermissaoDesassociar(
                grupo.getId(),
                permissaoModel.getId(),
                LinkRelation.of("desassociar"))));

        return permissoesModel;
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.adicionarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.removerPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

}
