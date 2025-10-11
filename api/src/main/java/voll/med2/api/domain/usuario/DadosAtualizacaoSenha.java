package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoSenha(
        @NotBlank(message = "Erro. Digite sua senha atual.")
        String senha,

        @NotBlank(message = "Erro. Digite uma senha.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~`\\\\|-]).{8,}$",
        message = "Senha precisa contar uma letra maiuscula, um caracter e pelo menos 8 digitos.")
        String novaSenha,

        @NotBlank(message = "Digite novamente a senha")
        String confirmacaoNovaSenha) {
}
