package voll.med2.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank
        String nome,

        @NotBlank
        String email,

        @NotBlank
        String telefone,

        @NotBlank(message = "Erro. CRM não informado.")
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotBlank
        Especialidade especialidade,

        @NotBlank @Valid
        DadosEndereco endereco) {
}
