package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank(message = "Campo login não pode ser vazio ou nulo.")
        @Size(min = 3, max = 50, message = "Login deve ter entre 3 e 50 caracteres.")
        @Email(message = "O login deve ser um email válido")
        String login,

        @NotBlank(message = "Campo login não pode ser vazio ou nulo.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]).{6,}$",
                message = "Senha deve ter pelo menos 6 caracteres, uma letra maiúscula, um número e um caracter especial"
        )
        String senha) {


/*
* (?=.*[A-Z]) → pelo menos uma letra maiúscula

(?=.*\d) → pelo menos um número

(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]) → pelo menos um caracter especial

.{6,} → mínimo 6 caracteres
 */
}
