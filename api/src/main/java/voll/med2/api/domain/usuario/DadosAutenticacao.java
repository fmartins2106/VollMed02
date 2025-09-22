package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(
        @NotBlank(message = "Digite o email.")
        String login,
        @NotBlank(message = "Digite a senha.")
        String senha) {
}
