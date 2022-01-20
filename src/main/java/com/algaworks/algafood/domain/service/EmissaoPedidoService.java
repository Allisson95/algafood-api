package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    public Pedido buscar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItensPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.totalizarPedido();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Long restauranteId = pedido.getRestaurante().getId();
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        Long clienteId = pedido.getCliente().getId();

        Restaurante restauranteSalvo = cadastroRestaurante.buscar(restauranteId);
        FormaPagamento formaPagamentoSalva = cadastroFormaPagamento.buscar(formaPagamentoId);
        Cidade cidadeSalva = cadastroCidade.buscar(cidadeId);
        Usuario usuarioSalvo = cadastroUsuario.buscar(clienteId);

        pedido.setRestaurante(restauranteSalvo);
        pedido.setFormaPagamento(formaPagamentoSalva);
        pedido.getEnderecoEntrega().setCidade(cidadeSalva);
        pedido.setCliente(usuarioSalvo);

        if (restauranteSalvo.naoAceitaFormaPagamento(formaPagamentoSalva)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamentoSalva.getDescricao()));
        }
    }

    private void validarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = pedido.getItens().stream()
                .map(itemPedido -> {
                    ItemPedido novoItemPedido = new ItemPedido();
                    novoItemPedido.setQuantidade(itemPedido.getQuantidade());
                    novoItemPedido.setObservacao(itemPedido.getObservacao());
                    novoItemPedido.setProduto(new Produto());
                    novoItemPedido.getProduto().setId(itemPedido.getProduto().getId());
                    return novoItemPedido;
                })
                .collect(Collectors.toMap(
                        itemPedido -> itemPedido.getProduto().getId(),
                        itemPedido -> itemPedido,
                        this::mesclarItensDuplicados))
                .values().stream()
                .map(itemPedido -> {
                    Long produtoId = itemPedido.getProduto().getId();
                    Produto produtoSalvo = cadastroProduto.buscar(pedido.getRestaurante().getId(), produtoId);

                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produtoSalvo);
                    itemPedido.setPrecoUnitario(produtoSalvo.getPreco());

                    return itemPedido;
                }).collect(Collectors.toList());

        pedido.setItens(itens);
    }

    private ItemPedido mesclarItensDuplicados(ItemPedido itemPedido, ItemPedido itemRepetido) {
        String observacaoConcatenada = itemPedido.getObservacao();

        if (itemRepetido.getObservacao() != null && !itemRepetido.getObservacao().isBlank()) {
            if (observacaoConcatenada == null) {
                observacaoConcatenada = itemRepetido.getObservacao();
            } else {
                observacaoConcatenada = observacaoConcatenada + " / "
                        + itemRepetido.getObservacao();
            }
        }

        itemPedido.setObservacao(observacaoConcatenada);
        itemPedido.setQuantidade(itemPedido.getQuantidade() + itemRepetido.getQuantidade());

        return itemPedido;
    }

}
