package voll.med2.api.domain.pacientes;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosAlteracaoPaciente(
        Long idpaciente,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco) {

}
