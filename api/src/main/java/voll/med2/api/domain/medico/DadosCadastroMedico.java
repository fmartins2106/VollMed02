package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco dadosEndereco) {
}
