package voll.med2.api.domain.medico;

public record DadosListagemMedicos(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

    public DadosListagemMedicos(Medico medico) {
        this(medico.getIdMedico(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
