package voll.med2.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank
        String logradouro,

        @NotBlank
        String endereco,

        String numero,
        String complemento,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        String uf) {
}
