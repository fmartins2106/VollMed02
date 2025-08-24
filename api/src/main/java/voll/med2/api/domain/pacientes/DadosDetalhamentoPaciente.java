package voll.med2.api.domain.pacientes;

import voll.med2.api.domain.endereco.DadosEndereco;
import voll.med2.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(
        Long idpaciente,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getIdpaciente(), paciente.getNome(), paciente.getEmail(),
                paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }
}
