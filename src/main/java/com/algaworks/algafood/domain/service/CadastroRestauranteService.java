package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
					.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);
		Cidade cidade = cadastroCidade.buscar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		restauranteSalvo.ativar();
	}

	@Transactional
	public void desativar(Long restauranteId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		restauranteSalvo.desativar();
	}

	@Transactional
    public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
    }

	@Transactional
	public void desativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::desativar);
	}

	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		restauranteSalvo.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		restauranteSalvo.fechar();
	}

	@Transactional
	public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		FormaPagamento formaPagamentoSalva = cadastroFormaPagamento.buscar(formaPagamentoId);

		restauranteSalvo.adicionarFormaPagamento(formaPagamentoSalva);
	}

	@Transactional
	public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		FormaPagamento formaPagamentoSalva = cadastroFormaPagamento.buscar(formaPagamentoId);

		restauranteSalvo.removerFormaPagamento(formaPagamentoSalva);
	}

	@Transactional
	public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		Usuario usuarioSalvo = cadastroUsuarioService.buscar(usuarioId);

		restauranteSalvo.adicionarResponsavel(usuarioSalvo);
	}

	@Transactional
	public void removerResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restauranteSalvo = buscar(restauranteId);
		Usuario usuarioSalvo = cadastroUsuarioService.buscar(usuarioId);

		restauranteSalvo.removerResponsavel(usuarioSalvo);
	}

}
