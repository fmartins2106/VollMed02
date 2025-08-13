package voll.med2.api.domain.paciente;

import voll.med2.api.domain.endereco.Endereco;

public record DadosCadastroPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {
}
