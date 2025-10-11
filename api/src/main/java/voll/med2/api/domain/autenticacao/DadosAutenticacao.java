package voll.med2.api.domain.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank(message = "Digite o email.")
        String login,
        @NotBlank(message = "Digite a senha.")
        String senha) {
}
