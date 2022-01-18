package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
public class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deve_atribuir_id_quando_cadastrar_cozinha_com_dados_validos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deve_falhar_quando_cadastrar_cozinha_sem_nome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException expectedError = assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});

		assertThat(expectedError).isNotNull();
	}

	@Test
	public void deve_falhar_quando_excluir_cozinha_em_uso() {
		Cozinha cozinhaEmUso = new Cozinha();
		cozinhaEmUso.setId(1L);

		EntidadeEmUsoException expectedError = assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(cozinhaEmUso.getId());
		});

		assertThat(expectedError).isNotNull();
	}

	@Test
	public void deve_falhar_quando_excluir_cozinha_inexistente() {
		CozinhaNaoEncontradaException expectedError = assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinha.excluir(100L);
		});

		assertThat(expectedError).isNotNull();
	}

}
