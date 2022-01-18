package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
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

	@Autowired
	private ObjectMapper mapper;
	
	@GetMapping("/teste")
	public List<Restaurante> teste(
			@RequestParam("nome") String nome,
			@RequestParam("taxaInicial") BigDecimal taxaInicial,
			@RequestParam("taxaFinal") BigDecimal taxaFinal
	) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable("restauranteId") Long restauranteId) {
		return cadastroRestaurante.buscar(restauranteId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestaurante.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(
			@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Restaurante restaurante
	) {
		try {
			Restaurante restauranteSalvo = cadastroRestaurante.buscar(restauranteId);

			BeanUtils.copyProperties(restaurante, restauranteSalvo, "id", "endereco", "formasPagamento", "dataCadastro", "produtos");

			return cadastroRestaurante.salvar(restauranteSalvo);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(
			@PathVariable("restauranteId") Long restauranteId,
			@RequestBody Map<String, Object> dados
	) {
		Restaurante restauranteSalvo = cadastroRestaurante.buscar(restauranteId);

		Restaurante novoRestaurante = merge(dados, restauranteSalvo, Restaurante.class);

		return atualizar(restauranteId, novoRestaurante);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T merge(Map<String, Object> data, T target, Class<T> clazz) {
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
