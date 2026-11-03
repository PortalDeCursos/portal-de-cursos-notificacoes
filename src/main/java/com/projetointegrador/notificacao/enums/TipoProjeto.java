package com.projetointegrador.notificacao.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoProjeto {

    TCC("TCC"),
    PROJETO_INTEGRADOR("Projeto Integrador"),
    PROJETO_PESQUISA("Projeto de Pesquisa"),
    PROJETO_EXTENSAO("Projeto de Extensão"),
    OUTROS("Outros");

    private final String descricao;
}
