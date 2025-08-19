package voll.med2.api.domain.pacientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.Endereco;

import java.util.Optional;

@Entity(name = "Paciente")
@Table(name = "dbpacientes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idpaciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpaciente;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    Endereco endereco;
    private boolean ativo = true;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
    }

    public void atualizarDados(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        Optional.ofNullable(dadosAtualizacaoPaciente.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.cpf()).ifPresent(s -> this.cpf = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.endereco()).ifPresent(s -> this.endereco = s);
    }

    public void excluir(Paciente paciente) {
        this.ativo = false;
    }
}
