package voll.med2.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico (
        @NotBlank(message = "Campo nome não pode ser vazio.")
        String nome,

        @NotBlank(message = "Campo email não pode ser vazio.")
        String email,

        @NotBlank(message = "Campo telefone não pode ser vazio.")
        String telefone,

        @NotBlank(message = "Campo crm não pode ser vazio.")
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull(message = "Campo não pode ser nulo.")
        Especialidade especialidade,

        @NotNull
        DadosEndereco endereco) {
}
