package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank(message = "Erro. Digite o nome completo.")
        @Pattern(regexp = "^[\\p{L}]+( [\\p{L}]+)+$")
        String nomeCompleto,

        @NotBlank(message = "Erro. Digite o email completo.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String login,

        @NotBlank(message = "Erro. Digite uma senha.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~`\\\\|-]).{8,}$",
                message = "Senha precisa contar uma letra maiuscula, um caracter e pelo menos 8 digitos.")
        String senha) {


/*
* (?=.*[A-Z]) → pelo menos uma letra maiúscula

(?=.*\d) → pelo menos um número

(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]) → pelo menos um caracter especial

.{6,} → mínimo 6 caracteres
 */
}
