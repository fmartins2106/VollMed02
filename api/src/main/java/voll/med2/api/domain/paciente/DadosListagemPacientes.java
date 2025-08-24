package voll.med2.api.domain.paciente;

import voll.med2.api.domain.endereco.Endereco;

public record DadosListagemPacientes(
        Long idpaciente,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public DadosListagemPacientes (Paciente paciente) {
        this(paciente.getIdpaciente(), paciente.getNome(), paciente.getEmail(),
                paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }
}
