package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        DadosEndereco endereco) {
}
