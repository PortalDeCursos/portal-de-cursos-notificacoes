package com.projetointegrador.notificacao.controller;

import com.projetointegrador.notificacao.dto.NotificacaoRequestDTO;
import com.projetointegrador.notificacao.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
@Tag(name = "Notificação", description = "Notificações dos Projetos do Portal de Cursos")
public class NotificacaoController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@Valid @RequestBody NotificacaoRequestDTO dto) {

        emailService.enviaEmail(dto);

        return ResponseEntity.ok().build();
    }
}
