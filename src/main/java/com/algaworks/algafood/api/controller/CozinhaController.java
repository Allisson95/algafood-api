package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@GetMapping
	public List<CozinhaModel> listar() {
		List<Cozinha> cozinhas = cozinhaRepository.findAll();
		return cozinhaModelAssembler.toCollectionModel(cozinhas);
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable("cozinhaId") Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomain(cozinhaInput);
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinha);
		return cozinhaModelAssembler.toModel(cozinhaSalva);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaSalva = cadastroCozinha.buscar(cozinhaId);

		cozinhaInputDisassembler.copyToDomain(cozinhaInput, cozinhaSalva);

		cozinhaSalva = cadastroCozinha.salvar(cozinhaSalva);

		return cozinhaModelAssembler.toModel(cozinhaSalva);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cozinhaId") Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

}
