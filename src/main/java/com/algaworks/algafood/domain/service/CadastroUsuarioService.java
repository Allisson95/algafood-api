package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	public Usuario buscar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);

		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuarioSalvo = buscar(usuarioId);
		usuarioSalvo.alterarSenha(senhaAtual, novaSenha);
	}

	@Transactional
	public void adicionarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuarioSalvo = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuarioSalvo.adicionarGrupo(grupo);
	}

	@Transactional
	public void removerGrupo(Long usuarioId, Long grupoId) {
		Usuario usuarioSalvo = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuarioSalvo.removerGrupo(grupo);
	}

}
