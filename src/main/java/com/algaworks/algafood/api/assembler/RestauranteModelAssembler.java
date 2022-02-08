package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToCidade;
import static com.algaworks.algafood.api.AlgaLinks.linkToCozinha;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurante;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteAbertura;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteAtivacao;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteFechamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteFormasPagamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteInativacao;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestauranteResponsavel;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurantes;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper mapper;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = mapper.map(restaurante, RestauranteModel.class);

        restauranteModel.add(linkToRestaurante(restaurante.getId()));
        restauranteModel.add(linkToRestaurantes());
        restauranteModel.getCozinha().add(linkToCozinha(restaurante.getCozinha().getId()));

        if (restauranteModel.getEndereco() != null) {
            Long cidadeId = restaurante.getEndereco().getCidade().getId();
            Long estadoId = restaurante.getEndereco().getCidade().getEstado().getId();

            restauranteModel.getEndereco().getCidade().add(linkToCidade(cidadeId));
            restauranteModel.getEndereco().getCidade().getEstado().add(linkToEstado(estadoId));
        }

        restauranteModel.add(linkToRestauranteFormasPagamento(restaurante.getId(), LinkRelation.of("formas-pagamento")));
        restauranteModel.add(linkToRestauranteResponsavel(restaurante.getId(), LinkRelation.of("responsaveis")));

        if (restaurante.podeAbrir()) {
            restauranteModel.add(linkToRestauranteAbertura(restaurante.getId(), LinkRelation.of("abrir")));
        }

        if (restaurante.podeFechar()) {
            restauranteModel.add(linkToRestauranteFechamento(restaurante.getId(), LinkRelation.of("fechar")));
        }

        if (restaurante.podeAtivar()) {
            restauranteModel.add(linkToRestauranteAtivacao(restaurante.getId(), LinkRelation.of("ativar")));
        }

        if (restaurante.podeDesativar()) {
            restauranteModel.add(linkToRestauranteInativacao(restaurante.getId(), LinkRelation.of("desativar")));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkToRestaurantes(IanaLinkRelations.SELF));
    }

}
