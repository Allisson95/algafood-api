package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        Email email = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .modelo(Modelo.builder()
                        .nome("pedido_cancelado.html")
                        .parametro("pedido", pedido)
                        .build())
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(email);
    }

}
