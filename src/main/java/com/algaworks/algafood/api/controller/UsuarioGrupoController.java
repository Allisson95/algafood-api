package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarioGrupo;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarioGrupoAssociar;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarioGrupoDesassociar;

import java.util.Set;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

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
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    @Override
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuarioSalvo = cadastroUsuario.buscar(usuarioId);
        Set<Grupo> grupos = usuarioSalvo.getGrupos();
        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(grupos)
                .mapLink(
                        IanaLinkRelations.SELF,
                        actualSelfLink -> linkToUsuarioGrupo(usuarioSalvo.getId(), actualSelfLink.getRel()))
                .add(linkToUsuarioGrupoAssociar(usuarioSalvo.getId(), LinkRelation.of("associar")));

        gruposModel.getContent().forEach(grupoModel -> grupoModel.add(linkToUsuarioGrupoDesassociar(
                usuarioSalvo.getId(),
                grupoModel.getId(),
                LinkRelation.of("desassociar"))));

        return gruposModel;
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.adicionarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.removerGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

}
