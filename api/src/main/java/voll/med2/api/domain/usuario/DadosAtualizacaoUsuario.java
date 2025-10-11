package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DadosAtualizacaoUsuario(
        @NotBlank(message = "Erro. Digite o nome completo.")
        @Pattern(regexp = "^[\\p{L}]+( [\\p{L}]+)+$")
        String nomeCompleto) {
}
