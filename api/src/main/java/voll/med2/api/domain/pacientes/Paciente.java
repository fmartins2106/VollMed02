package voll.med2.api.domain.pacientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private Endereco endereco;

    @NotNull
    private boolean ativo = true;


    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
    }

    public void atualizarDados(@Valid DadosAlteracaoPaciente dadosAlteracaoPaciente) {
        Optional.ofNullable(dadosAlteracaoPaciente.idpaciente()).ifPresent(s -> this.idpaciente = s);
        Optional.ofNullable(dadosAlteracaoPaciente.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAlteracaoPaciente.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAlteracaoPaciente.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAlteracaoPaciente.cpf()).ifPresent(s -> this.cpf = s);
        Optional.ofNullable(dadosAlteracaoPaciente.endereco()).ifPresent( dadosEndereco ->
                this.endereco.alterarDadosEndereco(dadosAlteracaoPaciente.endereco()));
    }

    public void excluir() {
        this.ativo = false;
    }
}
