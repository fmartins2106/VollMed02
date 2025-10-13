package voll.med2.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco) {
}
