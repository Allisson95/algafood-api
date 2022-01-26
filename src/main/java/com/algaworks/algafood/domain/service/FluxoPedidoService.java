package com.algaworks.algafood.domain.service;

import java.util.UUID;

import com.algaworks.algafood.domain.model.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedidoSalvo = emissaoPedido.buscar(codigoPedido);
        pedidoSalvo.confirmar();
    }

    @Transactional
    public void entregar(UUID codigoPedido) {
        Pedido pedidoSalvo = emissaoPedido.buscar(codigoPedido);
        pedidoSalvo.entregar();
    }

    @Transactional
    public void cancelar(UUID codigoPedido) {
        Pedido pedidoSalvo = emissaoPedido.buscar(codigoPedido);
        pedidoSalvo.cancelar();
    }

}
