package com.projetointegrador.notificacao.listener;

import com.projetointegrador.notificacao.config.RabbitConfig;
import com.projetointegrador.notificacao.event.ProjetoSubmetidoEvent;
import com.projetointegrador.notificacao.service.NotificacaoProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjetoSubmetidoListener {

    private final NotificacaoProjetoService notificacaoProjetoService;

    @RabbitListener(queues = RabbitConfig.QUEUE_PROJETO_SUBMETIDO)
    public void receber(ProjetoSubmetidoEvent evento) {

        notificacaoProjetoService.notificarProjetoSubmetido(evento);
    }
}
