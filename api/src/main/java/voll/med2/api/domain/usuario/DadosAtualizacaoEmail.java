package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoEmail(
        @NotBlank(message = "O e-mail não pode ser vazio")
        @Email(message = "O e-mail deve ser válido")
        String novoEmail,

        @NotBlank(message = "Senha não pode ser vazia")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]).{6,}$",
                message = "Senha deve ter pelo menos 6 caracteres, uma letra maiúscula, um número e um caracter especial")
        String novaSenha) {
}
