package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	@Override
	public CollectionModel<EstadoModel> listar() {
		List<Estado> estados = estadoRepository.findAll();
		return estadoModelAssembler.toCollectionModel(estados);
	}

	@GetMapping("/{estadoId}")
	@Override
	public EstadoModel buscar(@PathVariable("estadoId") Long estadoId) {
		Estado estado = cadastroEstado.buscar(estadoId);
		return estadoModelAssembler.toModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomain(estadoInput);
		Estado estadoSalvo = cadastroEstado.salvar(estado);
		return estadoModelAssembler.toModel(estadoSalvo);
	}

	@PutMapping("/{estadoId}")
	@Override
	public EstadoModel atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoSalvo = cadastroEstado.buscar(estadoId);

		estadoInputDisassembler.copyToDomain(estadoInput, estadoSalvo);

		estadoSalvo = cadastroEstado.salvar(estadoSalvo);

		return estadoModelAssembler.toModel(estadoSalvo);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void remover(@PathVariable("estadoId") Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}
