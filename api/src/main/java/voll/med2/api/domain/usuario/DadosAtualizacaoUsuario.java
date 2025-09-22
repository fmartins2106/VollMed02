package voll.med2.api.domain.usuario;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long idusuario,

        Boolean bloqueado,

        @NotNull
        @Future(message = "Data da expiração não pode ser menor que a data atual.")
        LocalDateTime expiracaoConta) {
}
