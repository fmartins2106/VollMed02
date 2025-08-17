package voll.med2.api.domain.paciente;

public record DadosListagemPacientes(
        Long idpaciente,
        String nome,
        String email,
        String telefone,
        String cpf) {

    public DadosListagemPacientes(Paciente paciente) {
        this(paciente.getIdpaciente(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(),paciente.getCpf());
    }
}
