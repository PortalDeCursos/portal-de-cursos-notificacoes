package com.projetointegrador.notificacao.listener;

import com.projetointegrador.notificacao.config.RabbitConfig;
import com.projetointegrador.notificacao.event.ProjetoSubmetidoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProjetoSubmetidoListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_PROJETO_SUBMETIDO)
    public void receber(ProjetoSubmetidoEvent evento) {

        log.info("Projeto submetido recebido: id={}, nome={}, tipo={}, status={}, autores={}, professores={}",
                evento.projetoId(),
                evento.nomeProjeto(),
                evento.tipoProjeto(),
                evento.statusProjeto(),
                evento.autores(),
                evento.professores());
    }
}
