package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler implements Disassembler<FormaPagamentoInput, FormaPagamento> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public FormaPagamento toDomain(FormaPagamentoInput input) {
		return mapper.map(input, FormaPagamento.class);
	}

	@Override
	public void copyToDomain(FormaPagamentoInput input, FormaPagamento domain) {
		mapper.map(input, domain);
	}

}
