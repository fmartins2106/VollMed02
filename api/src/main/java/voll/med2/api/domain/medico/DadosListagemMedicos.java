package voll.med2.api.domain.medico;

public record DadosListagemMedicos(
        Long idmedico,
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

    public DadosListagemMedicos(Medico medico) {
        this(medico.getIdmedico(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
