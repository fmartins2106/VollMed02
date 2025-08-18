package voll.med2.api.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.DadosEndereco;
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
    //@Column(name = "idpaciente")
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

    public void atualizarInformacoesPaciente(@Valid DadosAtualizacaoPacientes dadosAtualizacaoPacientes) {
        Optional.ofNullable(dadosAtualizacaoPacientes.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAtualizacaoPacientes.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAtualizacaoPacientes.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAtualizacaoPacientes.endereco()).ifPresent(s -> this.endereco.atualizarInformacoes(s));
    }

    public void excluir() {
        this.ativo = false;
    }
}
