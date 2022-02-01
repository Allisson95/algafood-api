package com.algaworks.algafood.domain.service;

import java.util.UUID;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedidoSalvo = emissaoPedido.buscar(codigoPedido);
        pedidoSalvo.confirmar();

        Email email = Email.builder()
                .assunto(pedidoSalvo.getRestaurante().getNome() + " - Pedido confirmado")
                .modelo(Modelo.builder()
                        .nome("pedido_confirmado.html")
                        .parametro("pedido", pedidoSalvo)
                        .build())
                .destinatario(pedidoSalvo.getCliente().getEmail())
                .build();

        envioEmail.enviar(email);
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
