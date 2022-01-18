package com.algaworks.algafood.api.assembler;

public interface Disassembler<S, D> {

	D toDomain(S input);

	void copyToDomain(S input, D domain);

}