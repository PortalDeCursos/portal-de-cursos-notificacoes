package com.projetointegrador.notificacao.service;

import com.projetointegrador.notificacao.dto.NotificacaoRequestDTO;
import com.projetointegrador.notificacao.enums.StatusProjeto;
import com.projetointegrador.notificacao.enums.TipoProjeto;
import com.projetointegrador.notificacao.event.ProjetoSubmetidoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoProjetoService {

    private final EmailService emailService;
    private final Destinario destinario;

    public void notificarProjetoSubmetido(ProjetoSubmetidoEvent evento) {

        String nomeResponsavel = evento.autores().get(0).nome();

        notificarAutores(evento, nomeResponsavel);
        notificarOrientador(evento, nomeResponsavel);
    }

    private void notificarAutores(ProjetoSubmetidoEvent evento, String nomeResponsavel) {

        List<String> emails = evento.autores().stream()
                .map(ProjetoSubmetidoEvent.AutorEvent::email)
                .toList();

        enviar(evento, emails, nomeResponsavel);
    }

    private void notificarOrientador(ProjetoSubmetidoEvent evento, String nomeResponsavel) {

        evento.professores().stream()
                .filter(p -> "ORIENTADOR".equals(p.papel()))
                .findFirst()
                .ifPresent(p -> {
                    String email = destinario.emailDoProfessor(p.professorId());
                    enviar(evento, List.of(email), nomeResponsavel);
                });
    }

    private void enviar(ProjetoSubmetidoEvent evento, List<String> destinatarios, String nomeResponsavel) {

        try {
            NotificacaoRequestDTO dto = new NotificacaoRequestDTO();
            dto.setStatusProjeto(StatusProjeto.valueOf(evento.statusProjeto()));
            dto.setNomeProjeto(evento.nomeProjeto());
            dto.setTipoProjeto(TipoProjeto.valueOf(evento.tipoProjeto()));
            dto.setDestinatarios(destinatarios);
            dto.setNomeResponsavel(nomeResponsavel);
            dto.setDataHora(evento.dataCadastro());

            emailService.enviaEmail(dto);

        } catch (Exception e) {
            log.error("Falha ao enviar e-mail do projeto {} para {}: {}",
                    evento.projetoId(), destinatarios, e.getMessage());
        }
    }
}
