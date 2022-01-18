package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public List<Cidade> listar() {
		return this.cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long cidadeId) {
		Optional<Cidade> cidade = this.cidadeRepository.findById(cidadeId);

		if (cidade.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(cidade.get());
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			Cidade cidadeSalva = this.cadastroCidade.salvar(cidade);

			return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {
		try {
			Optional<Cidade> cidadeSalva = this.cidadeRepository.findById(cidadeId);

			if (cidadeSalva.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			BeanUtils.copyProperties(cidade, cidadeSalva.get(), "id");

			Cidade novaCidade = this.cadastroCidade.salvar(cidadeSalva.get());

			return ResponseEntity.ok(novaCidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable("cidadeId") Long cidadeId) {
		try {
			this.cadastroCidade.excluir(cidadeId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
