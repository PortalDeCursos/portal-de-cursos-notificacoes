package com.projetointegrador.notificacao.listener;

import com.projetointegrador.notificacao.config.RabbitConfig;
import com.projetointegrador.notificacao.event.ProjetoStatusAlteradoEvent;
import com.projetointegrador.notificacao.service.NotificacaoProjetoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjetoStatusListener {

    private final NotificacaoProjetoService notificacaoProjetoService;

    @RabbitListener(queues = RabbitConfig.QUEUE_PROJETO_STATUS)
    public void receber(ProjetoStatusAlteradoEvent evento) {

        log.info("[RabbitMQ] Evento de status recebido: projetoId={}, status={}",
                evento.projetoId(), evento.statusProjeto());

        notificacaoProjetoService.notificarStatusAlterado(evento);
    }
}
