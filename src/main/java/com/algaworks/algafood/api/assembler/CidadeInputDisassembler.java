package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler implements Disassembler<CidadeInput, Cidade> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Cidade toDomain(CidadeInput input) {
		return mapper.map(input, Cidade.class);
	}

	@Override
	public void copyToDomain(CidadeInput input, Cidade domain) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance
		// of com.algaworks.algafood.domain.model.Estado was altered from 2 to 3
		domain.setEstado(new Estado());

		mapper.map(input, domain);
	}

}
