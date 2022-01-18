package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler implements Disassembler<UsuarioInput, Usuario> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Usuario toDomain(UsuarioInput input) {
		return mapper.map(input, Usuario.class);
	}

	@Override
	public void copyToDomain(UsuarioInput input, Usuario domain) {
		mapper.map(input, domain);
	}

}
