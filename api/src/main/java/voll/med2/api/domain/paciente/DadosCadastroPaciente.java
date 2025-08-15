package voll.med2.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,

        @NotBlank
        String email,

        @NotBlank
        String cpf,

        @NotBlank
        String telefone,

        @NotNull
        @Valid // Faz a validação dos campos internos do objeto 'endereco'
        DadosEndereco endereco) {
}
