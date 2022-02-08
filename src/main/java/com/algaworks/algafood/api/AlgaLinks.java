package com.algaworks.algafood.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AlgaLinks {

    public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
            TemplateVariable.requestParameter("page"),
            TemplateVariable.requestParameter("size"),
            TemplateVariable.requestParameter("sort"));

    public static Link linkToPedido(UUID codigoPedido) {
        return linkToPedido(codigoPedido, IanaLinkRelations.SELF);
    }

    public static Link linkToPedido(UUID codigoPedido, LinkRelation rel) {
        return linkTo(methodOn(PedidoController.class).buscar(codigoPedido)).withRel(rel);
    }

    public static Link linkToPedidos() {
        String pedidosUrl = linkTo(methodOn(PedidoController.class).pesquisar(null, null)).toUri().toString();

        TemplateVariables filterVariables = new TemplateVariables(
                TemplateVariable.requestParameter("clienteId"),
                TemplateVariable.requestParameter("restauranteId"),
                TemplateVariable.requestParameter("dataCriacaoInicio"),
                TemplateVariable.requestParameter("dataCriacaoFim"));

        return Link.of(UriTemplate.of(pedidosUrl, filterVariables.concat(PAGE_VARIABLES)), "pedidos");
    }

    public static Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToRestaurantes(LinkRelation rel) {
        return linkTo(methodOn(RestauranteController.class).listar()).withRel(rel);
    }

    public static Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF);
    }

    public static Link linkToRestaurante(Long restauranteId, LinkRelation rel) {
        return linkTo(methodOn(RestauranteController.class).buscar(restauranteId))
                .withRel(rel);
    }

    public static Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToUsuarios(LinkRelation rel) {
        return linkTo(methodOn(UsuarioController.class).listar())
                .withRel(rel);
    }

    public static Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF);
    }

    public static Link linkToUsuario(Long usuarioId, LinkRelation rel) {
        return linkTo(methodOn(UsuarioController.class).buscar(usuarioId))
                .withRel(rel);
    }

    public static Link linkToUsuarioGrupo(Long usuarioId) {
        return linkToUsuarioGrupo(usuarioId, IanaLinkRelations.SELF);
    }

    public static Link linkToUsuarioGrupo(Long usuarioId, LinkRelation rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId))
                .withRel(rel);
    }

    public static Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToFormasPagamento(LinkRelation rel) {
        return linkTo(methodOn(FormaPagamentoController.class).listar())
                .withRel(rel);
    }

    public static Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF);
    }

    public static Link linkToFormaPagamento(Long formaPagamentoId, LinkRelation rel) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId))
                .withRel(rel);
    }

    public static Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToCidades(LinkRelation rel) {
        return linkTo(methodOn(CidadeController.class).listar())
                .withRel(rel);
    }

    public static Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF);
    }

    public static Link linkToCidade(Long cidadeId, LinkRelation rel) {
        return linkTo(methodOn(CidadeController.class).buscar(cidadeId))
                .withRel(rel);
    }

    public static Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToCozinhas(LinkRelation rel) {
        String cozinhasUrl = linkTo(methodOn(CozinhaController.class).listar(null)).toUri().toString();
        return Link.of(UriTemplate.of(cozinhasUrl, PAGE_VARIABLES), rel);
    }

    public static Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF);
    }

    public static Link linkToCozinha(Long cozinhaId, LinkRelation rel) {
        return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withRel(rel);
    }

    public static Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.COLLECTION);
    }

    public static Link linkToEstados(LinkRelation rel) {
        return linkTo(methodOn(EstadoController.class).listar())
                .withRel(rel);
    }

    public static Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF);
    }

    public static Link linkToEstado(Long estadoId, LinkRelation rel) {
        return linkTo(methodOn(EstadoController.class).buscar(estadoId))
                .withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId, LinkRelation rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId)).withRel(rel);
    }

}
