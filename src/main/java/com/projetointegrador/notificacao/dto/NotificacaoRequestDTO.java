package com.projetointegrador.notificacao.dto;

import com.projetointegrador.notificacao.enums.StatusProjeto;
import com.projetointegrador.notificacao.enums.TipoProjeto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoRequestDTO {

    @NotNull(message = "O status do projeto é obrigatório")
    private StatusProjeto statusProjeto;

    @NotBlank(message = "O nome do projeto é obrigatório")
    private String nomeProjeto;

    @NotNull(message = "O tipo do projeto é obrigatório")
    private TipoProjeto tipoProjeto;

    @NotEmpty(message = "Informe ao menos um destinatário")
    private List<@Email(message = "E-mail de destinatário inválido") String> destinatarios;

    private String justificativa;
}
