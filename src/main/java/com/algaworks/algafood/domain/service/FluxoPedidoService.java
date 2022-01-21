package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedidoSalvo = emissaoPedido.buscar(pedidoId);
        pedidoSalvo.confirmar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedidoSalvo = emissaoPedido.buscar(pedidoId);
        pedidoSalvo.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedidoSalvo = emissaoPedido.buscar(pedidoId);
        pedidoSalvo.cancelar();
    }

}
