package voll.med2.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;

public record DadosEndereco(
        @NotBlank(message = "Campo Logradouro não pode ser vazio.")
        String logradouro,

        @NotBlank(message = "Campo endereço não pode ser vazio.")
        String endereco,
        String numero,
        String complemento,

        @NotBlank(message = "Campo cep não pode ser vazio.")
        String cep,

        @NotBlank(message = "Campo bairro não pode ser vazio.")
        String bairro,

        @NotBlank(message = "Campo cidade não pode ser vazio.")
        String cidade,

        @NotBlank(message = "Campo uf não pode ser vazio.")
        String uf) {
}
