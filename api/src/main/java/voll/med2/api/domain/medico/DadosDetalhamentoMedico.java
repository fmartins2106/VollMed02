package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(
        Long idmedico,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco) {


    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getIdmedico(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
                medico.getCrm(), medico.getEspecialidade(),medico.getEndereco());
    }
}
