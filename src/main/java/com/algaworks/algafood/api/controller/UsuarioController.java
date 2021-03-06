package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.NovaSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	@Override
	public CollectionModel<UsuarioModel> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioModelAssembler.toCollectionModel(usuarios);
	}

	@GetMapping("/{usuarioId}")
	@Override
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscar(usuarioId);
		return usuarioModelAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomain(usuarioInput);
		Usuario usuarioSalvo = cadastroUsuario.salvar(usuario);
		return usuarioModelAssembler.toModel(usuarioSalvo);
	}

	@PutMapping("/{usuarioId}")
	@Override
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioSalvo = cadastroUsuario.buscar(usuarioId);
		usuarioInputDisassembler.copyToDomain(usuarioInput, usuarioSalvo);
		usuarioSalvo = cadastroUsuario.salvar(usuarioSalvo);
		return usuarioModelAssembler.toModel(usuarioSalvo);
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid NovaSenhaInput novaSenhaInput) {
		cadastroUsuario.alterarSenha(usuarioId, novaSenhaInput.getSenhaAtual(), novaSenhaInput.getNovaSenha());
	}

}
