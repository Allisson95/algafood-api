package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToFormaPagamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToFormasPagamento;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper mapper;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento entity) {
        FormaPagamentoModel formaPagamentoModel = mapper.map(entity, FormaPagamentoModel.class);

        formaPagamentoModel.add(linkToFormaPagamento(entity.getId()));
        formaPagamentoModel.add(linkToFormasPagamento());

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(linkToFormasPagamento(IanaLinkRelations.SELF));
    }

}
