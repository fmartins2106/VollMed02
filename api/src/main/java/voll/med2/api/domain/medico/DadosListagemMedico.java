package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.Endereco;

public record DadosListagemMedico(
        Long idmedico,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getIdmedico(), medico.getNome(), medico.getEmail(), medico.getCrm(),
                medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
