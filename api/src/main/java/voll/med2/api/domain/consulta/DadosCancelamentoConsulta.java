package voll.med2.api.domain.consulta;

import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idconsulta,

        @NotNull(message = "Erro, digite a data da consulta.")
        LocalDateTime dataHoraConsulta,

        @NotNull
        MotivoCancelamento motivoCancelamento) {
}
