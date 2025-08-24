package voll.med2.api.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
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
    private Long idpaciente;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    @Valid
    private Endereco endereco;
    private boolean ativo = true;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
    }


    public void atualizarDados(DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        Optional.ofNullable(dadosAtualizacaoPaciente.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.cpf()).ifPresent(s -> this.cpf = s);
        Optional.ofNullable(dadosAtualizacaoPaciente.endereco()).ifPresent(s ->
                endereco.alterarDadosEndereco(dadosAtualizacaoPaciente.endereco()));
    }

    public void excluir() {
        this.ativo = false;
    }
}
