package voll.med2.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "Erro. Campo logradouro não pode ser vazio.")
        String logradouro,

        @NotBlank(message = "Erro. Campo endereço não pode ser vazio.")
        String endereco,
        String numero,
        String complemento,

        @Pattern(regexp = "\\d{8}")
        @NotBlank(message = "Erro. Campo cep não pode ser vazio e deve conter 8 números.")
        String cep,

        @NotBlank(message = "Erro. Campo bairro não pode ser vazio.")
        String bairro,

        @NotBlank(message = "Erro. Campo cidade não pode ser vazio.")
        String cidade,

        @NotBlank(message = "Erro. Campo uf não pode ser vazio.")
        String uf) {
}
