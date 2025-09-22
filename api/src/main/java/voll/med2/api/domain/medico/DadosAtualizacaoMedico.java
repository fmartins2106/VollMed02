package voll.med2.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        Long idmedico,

        @NotBlank(message = "Erro. Campo nome não pode ser vazio.")
        String nome,

        @NotBlank(message = "Erro. Campo email não pode ser vazio.")
        String email,

        @NotBlank(message = "Erro. Campo telefone não pode ser vazio.")
        String telefone,

        @NotBlank(message = "Erro. Campo crm não pode ser vazio.")
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        DadosEndereco endereco) {
}
