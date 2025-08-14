package voll.med2.api.domain.paciente;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        String nome,
        String email,
        String cpf,
        String telefone,
        DadosEndereco endereco) {
}
