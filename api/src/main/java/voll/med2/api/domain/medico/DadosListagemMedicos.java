package voll.med2.api.domain.medico;

import voll.med2.api.domain.endereco.Endereco;

public record DadosListagemMedicos(
        Long idmedico,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosListagemMedicos (Medico medico){
        this(medico.getIdmedico(), medico.getNome(), medico.getEmail(),
                medico.getTelefone(), medico.getCrm(), medico.getEspecialidade()
        ,medico.getEndereco());
    }
}
