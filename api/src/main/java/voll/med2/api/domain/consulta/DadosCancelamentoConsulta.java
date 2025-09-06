package voll.med2.api.domain.consulta;

import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idconsulta,

        @NotNull
        MotivoCancelamento motivoCancelamento) {
}
