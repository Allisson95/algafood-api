package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email;
import com.algaworks.algafood.domain.service.EnvioEmailService.Email.Modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    // @EventListener
    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        Email email = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .modelo(Modelo.builder()
                        .nome("pedido_confirmado.html")
                        .parametro("pedido", pedido)
                        .build())
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(email);
    }

}
