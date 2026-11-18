package com.projetointegrador.notificacao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Destinario {

    // PROVISÓRIO: trocar pela consulta ao msUsuarios quando a integração com msProjetos estiver pronta
    @Value("${notificacao.email-orientador-provisorio}")
    private String emailOrientadorProvisorio;

    public String emailDoProfessor(Long professorId) {
        return emailOrientadorProvisorio;
    }
}
