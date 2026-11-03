package com.projetointegrador.notificacao.service;

import com.projetointegrador.notificacao.dto.NotificacaoRequestDTO;
import com.projetointegrador.notificacao.enums.StatusProjeto;
import com.projetointegrador.notificacao.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    private String remetente;

    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    @Value("${portal.submissao-url:#}")
    private String linkSubmissao;

    public void enviaEmail(NotificacaoRequestDTO dto) {

        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(remetente, nomeRemetente));
            helper.setTo(dto.getDestinatarios().toArray(new String[0]));
            helper.setSubject(montarAssunto(dto.getStatusProjeto()));

            Context context = new Context();
            context.setVariable("titulo", montarAssunto(dto.getStatusProjeto()));
            context.setVariable("mensagem", montarMensagem(dto.getStatusProjeto()));
            context.setVariable("nomeProjeto", dto.getNomeProjeto());
            context.setVariable("tipoProjeto", dto.getTipoProjeto().getDescricao());
            context.setVariable("justificativa", dto.getJustificativa());

            LocalDateTime dataHora = dto.getDataHora() != null ? dto.getDataHora() : LocalDateTime.now();

            context.setVariable("rotuloResponsavel", montarRotuloResponsavel(dto.getStatusProjeto()));
            context.setVariable("nomeResponsavel", dto.getNomeResponsavel());
            context.setVariable("dataHora", dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")));
            context.setVariable("linkSubmissao", linkSubmissao);

            String html = templateEngine.process("notificacao", context);
            helper.setText(html, true);

            javaMailSender.send(mensagem);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar o email", e);
        }
    }

    private String montarAssunto(StatusProjeto status) {

        return switch (status) {

            case EM_ANALISE -> "Projeto em análise";

            case APROVADO -> "Projeto aprovado pelo orientador";

            case RECUSADO -> "Projeto recusado pelo orientador";

            case ANALISE_PUBLICACAO -> "Projeto aguardando análise de publicação";

            case PUBLICADO -> "Projeto publicado";

            case RECUSADO_PUBLICACAO -> "Publicação do projeto recusada";

            case REVISAO -> "Projeto em revisão";
        };
    }

    private String montarMensagem(StatusProjeto status) {

        return switch (status) {

            case EM_ANALISE -> "O projeto foi submetido e está em análise.";

            case APROVADO -> "O projeto foi aprovado pelo professor orientador.";

            case RECUSADO -> "O projeto foi recusado pelo professor orientador.";

            case ANALISE_PUBLICACAO -> "Há um projeto aguardando a sua análise para publicação.";

            case PUBLICADO -> "O projeto foi aprovado e já está publicado no portal.";

            case RECUSADO_PUBLICACAO -> "A publicação do projeto foi recusada pelo coordenador.";

            case REVISAO -> "O projeto precisa de revisão.";
        };
    }

    private String montarRotuloResponsavel(StatusProjeto status) {

        return switch (status) {

            case EM_ANALISE, ANALISE_PUBLICACAO, REVISAO -> "Enviado por";

            case APROVADO -> "Aprovado por";

            case RECUSADO, RECUSADO_PUBLICACAO -> "Recusado por";

            case PUBLICADO -> "Publicado por";
        };
    }
}
