package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler implements Assembler<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public UsuarioModel toModel(Usuario source) {
		return mapper.map(source, UsuarioModel.class);
	}

}
