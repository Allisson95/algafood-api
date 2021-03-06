package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
// import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
// import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
// import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

//import javax.validation.ConstraintViolationException;

//import org.flywaydb.core.Flyway;
//import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

//import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
//import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
//import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	private static final Long COZINHA_ID_INEXISTENTE = 100L;

	private int quantidadeCozinhasCadastradas;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		fillTestData();
	}

	@Test
	void deve_retornar_status_200_quando_consultar_cozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}

	@Test
	void deve_retornar_todas_cozinhas_quando_consultar_cozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200)
			.body("", hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	void deve_retornar_status_201_quando_cadastrar_cozinha() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(novaCozinha)
		.when()
			.post()
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("nome", equalTo(novaCozinha.getNome()));
	}

	@Test
	void deve_retornar_status_200_quando_consultar_cozinha_existente() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		novaCozinha = cozinhaRepository.save(novaCozinha);

		given()
			.accept(ContentType.JSON)
			.pathParam("cozinhaId", novaCozinha.getId())
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(200)
			.body("nome", equalTo(novaCozinha.getNome()));
	}

	@Test
	void deve_retornar_status_404_quando_consultar_cozinha_inexistente() {
		given()
			.accept(ContentType.JSON)
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(404);
	}

	private void fillTestData() {
		Cozinha cozinhaIndiana = new Cozinha();
		cozinhaIndiana.setNome("Indiana");

		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");

		cozinhaRepository.saveAll(Arrays.asList(cozinhaIndiana, cozinhaTailandesa));
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}

//	@Autowired
//	private CadastroCozinhaService cadastroCozinha;
//
//	@Test
//	public void deve_atribuir_id_quando_cadastrar_cozinha_com_dados_validos() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//
//		novaCozinha = cadastroCozinha.salvar(novaCozinha);
//
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//
//	@Test
//	public void deve_falhar_quando_cadastrar_cozinha_sem_nome() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//
//		ConstraintViolationException expectedError = assertThrows(ConstraintViolationException.class, () -> {
//			cadastroCozinha.salvar(novaCozinha);
//		});
//
//		assertThat(expectedError).isNotNull();
//	}
//
//	@Test
//	public void deve_falhar_quando_excluir_cozinha_em_uso() {
//		Cozinha cozinhaEmUso = new Cozinha();
//		cozinhaEmUso.setId(1L);
//
//		EntidadeEmUsoException expectedError = assertThrows(EntidadeEmUsoException.class, () -> {
//			cadastroCozinha.excluir(cozinhaEmUso.getId());
//		});
//
//		assertThat(expectedError).isNotNull();
//	}
//
//	@Test
//	public void deve_falhar_quando_excluir_cozinha_inexistente() {
//		CozinhaNaoEncontradaException expectedError = assertThrows(CozinhaNaoEncontradaException.class, () -> {
//			cadastroCozinha.excluir(100L);
//		});
//
//		assertThat(expectedError).isNotNull();
//	}

}
