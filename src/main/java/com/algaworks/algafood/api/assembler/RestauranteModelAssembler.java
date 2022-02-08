package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
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

		restauranteModel
				.add(linkTo(methodOn(RestauranteController.class).buscar(restauranteModel.getId())).withSelfRel());
		restauranteModel
				.add(linkTo(methodOn(RestauranteController.class).listar()).withRel(IanaLinkRelations.COLLECTION));

		restauranteModel.getCozinha().add(
				linkTo(methodOn(CozinhaController.class).buscar(restauranteModel.getCozinha().getId())).withSelfRel());

		if (restauranteModel.getEndereco() != null) {
			restauranteModel.getEndereco().getCidade().add(
					linkTo(methodOn(CidadeController.class).buscar(restauranteModel.getEndereco().getCidade().getId()))
							.withSelfRel());

			restauranteModel.getEndereco().getCidade().getEstado().add(
					linkTo(methodOn(EstadoController.class)
							.buscar(restauranteModel.getEndereco().getCidade().getEstado().getId()))
									.withSelfRel());
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(methodOn(RestauranteController.class).listar()).withSelfRel());
	}

}
