package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler implements Disassembler<CozinhaInput, Cozinha> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Cozinha toDomain(CozinhaInput input) {
		return mapper.map(input, Cozinha.class);
	}

	@Override
	public void copyToDomain(CozinhaInput input, Cozinha domain) {
		mapper.map(input, domain);
	}

}
