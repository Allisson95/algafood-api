package com.algaworks.algafood.domain.service;

import java.util.UUID;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(UUID codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.entregar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(UUID codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }

}
