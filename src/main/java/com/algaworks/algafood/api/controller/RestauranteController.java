package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public List<Restaurante> listar() {
		return this.restauranteRepository.listar();
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long restauranteId) {
		Restaurante restaurante = this.restauranteRepository.buscar(restauranteId);

		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteSalvo = this.cadastroRestaurante.salvar(restaurante);

			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(
			@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Restaurante restaurante
	) {
		try {
			Restaurante restauranteSalvo = this.restauranteRepository.buscar(restauranteId);

			if (restauranteSalvo == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			BeanUtils.copyProperties(restaurante, restauranteSalvo, "id");

			restauranteSalvo = this.cadastroRestaurante.salvar(restauranteSalvo);

			return ResponseEntity.ok(restauranteSalvo);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(
			@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Map<String, Object> dados
	) {
		Restaurante restauranteSalvo = this.restauranteRepository.buscar(restauranteId);

		if (restauranteSalvo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		restauranteSalvo = this.merge(dados, restauranteSalvo, Restaurante.class);

		return this.atualizar(restauranteId, restauranteSalvo);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T merge(Map<String, Object> data, T target, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T convertedValue = mapper.convertValue(data, clazz);

		data.forEach((propName, propValue) -> {
			Field field = ReflectionUtils.findField(clazz, propName);
			field.setAccessible(true);

			Object newValue;
			if (propValue instanceof Map) {
				newValue = merge(
						(Map<String, Object>) propValue,
						(T) ReflectionUtils.getField(field, target),
						(Class<T>) field.getType()
					);
			} else {
				newValue = ReflectionUtils.getField(field, convertedValue);
			}

			ReflectionUtils.setField(field, target, newValue);
		});

		return target;
	}
}
