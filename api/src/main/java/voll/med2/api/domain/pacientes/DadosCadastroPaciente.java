package voll.med2.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,

        @NotBlank
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}.?\\d{3}.?\\d{3}-?\\d{2}")
        String cpf,

        @NotNull
        @Valid
        DadosEndereco endereco) {

}
