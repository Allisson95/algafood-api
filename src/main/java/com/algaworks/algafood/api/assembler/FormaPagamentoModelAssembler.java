package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler implements Assembler<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public FormaPagamentoModel toModel(FormaPagamento source) {
		return mapper.map(source, FormaPagamentoModel.class);
	}

}
