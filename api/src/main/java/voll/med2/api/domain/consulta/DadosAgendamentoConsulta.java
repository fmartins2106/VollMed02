package voll.med2.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import voll.med2.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @NotBlank(message = "Erro. Necessário informar o id do médico.")
        Long idmedico,

        @NotBlank(message = "Erro. Necessário informar o id do paciente.")
        Long idpaciente,

        @NotBlank(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.")
        @Future
        LocalDateTime dataConsulta,

        @NotNull
        Especialidade especialidade) {
}
