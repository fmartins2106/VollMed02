package voll.med2.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPacientes(
        @NotNull
        @Positive
        Long idpaciente,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco) {
}
