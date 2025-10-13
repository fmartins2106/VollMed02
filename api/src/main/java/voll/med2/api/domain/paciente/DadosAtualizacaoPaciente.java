package voll.med2.api.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        Long idpaciente,

        String nome,

        String email,

        String telefone,

        String cpf,

        DadosEndereco endereco) {


}
