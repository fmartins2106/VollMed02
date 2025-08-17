package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosDetalhamentoMedico(
        Long idMedico,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco) {


    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getIdMedico(), medico.getNome(), medico.getEmail(), medico.getTelefone(),
                medico.getCrm(), medico.getEspecialidade(),medico.getEndereco());
    }
}
