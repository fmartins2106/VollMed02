package voll.med2.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedicos(
        @NotNull
        @Positive
        Long idmedico,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco) {
}
