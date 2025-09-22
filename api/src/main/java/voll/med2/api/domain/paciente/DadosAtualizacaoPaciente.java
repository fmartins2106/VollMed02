package voll.med2.api.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        Long idpaciente,
        @NotBlank(message = "Campo nome não pode ser vazio.")
        String nome,

        @NotBlank(message = "Campo nome não pode ser vazio.")
        String email,

        @NotBlank(message = "Campo nome não pode ser vazio.")
        String telefone,

        @NotBlank(message = "Campo nome não pode ser vazio.")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,

        DadosEndereco endereco) {


}
