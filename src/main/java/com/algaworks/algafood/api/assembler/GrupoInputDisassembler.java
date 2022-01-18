package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler implements Disassembler<GrupoInput, Grupo> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Grupo toDomain(GrupoInput input) {
		return mapper.map(input, Grupo.class);
	}

	@Override
	public void copyToDomain(GrupoInput input, Grupo domain) {
		mapper.map(input, domain);
	}

}
