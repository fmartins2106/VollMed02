package voll.med2.api.domain.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosTokenJTW(
        @NotBlank String tokenJWT,
        @NotBlank String refreshToken) {
}
