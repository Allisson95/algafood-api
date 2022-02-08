package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToUsuario;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarioGrupo;
import static com.algaworks.algafood.api.AlgaLinks.linkToUsuarios;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper mapper;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario entity) {
        UsuarioModel usuarioModel = mapper.map(entity, UsuarioModel.class);

        usuarioModel.add(linkToUsuario(entity.getId()));
        usuarioModel.add(linkToUsuarios());
        usuarioModel.add(linkToUsuarioGrupo(entity.getId(), LinkRelation.of("grupos-usuario")));

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkToUsuarios(IanaLinkRelations.SELF));
    }

}
