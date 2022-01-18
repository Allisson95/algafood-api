package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhas;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhas.listar();
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
		Cozinha cozinha = cozinhas.buscar(cozinhaId);

		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhas.buscar(cozinhaId);

		if (cozinhaAtual == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		Cozinha novaCozinha = cadastroCozinha.salvar(cozinhaAtual);

		return ResponseEntity.ok(novaCozinha);
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
