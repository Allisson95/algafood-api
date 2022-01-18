package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler implements Disassembler<EstadoInput, Estado> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Estado toDomain(EstadoInput input) {
		return mapper.map(input, Estado.class);
	}

	@Override
	public void copyToDomain(EstadoInput input, Estado estado) {
		mapper.map(input, estado);
	}

}
