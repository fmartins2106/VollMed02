package voll.med2.api.domain.paciente;

import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosDetalhamentoPaciente(
        Long idPaciente,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getIdpaciente(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(),
                paciente.getCpf(),paciente.getEndereco());
    }
}
