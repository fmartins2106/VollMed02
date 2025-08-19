package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.Endereco;

public record DadosAtualizacaoMedicos(
        Long idmedico,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco) {
}
