package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DadosAtualizacaoRoles(
        @NotBlank(message = "A lista de roles não pode ser vazia")
        List<String> roles) {
}
