package voll.med2.api.domain.pacientes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank(message = "Campo nome não pode ser vazio.")
        String nome,

        @NotBlank(message = "Campo email não pode ser vazio.")
        String email,

        @NotBlank(message = "Campo telefone não pode ser vazio.")
        String telefone,

        @NotBlank(message = "Campo cpf não pode ser vazio.")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,

        @NotNull
        DadosEndereco endereco) {
}
