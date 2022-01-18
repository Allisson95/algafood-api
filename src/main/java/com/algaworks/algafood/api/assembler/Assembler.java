package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Assembler<S, D> {

	D toModel(S source);

	default List<D> toCollectionModel(Collection<S> sources) {
		Objects.requireNonNull(sources, () -> "Sources cannot be null");
		return sources.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
