package voll.med2.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.Endereco;

import java.util.Optional;

@Entity(name = "Medico")
@Table(name = "dbmedicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idmedico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmedico;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    @Valid
    private Endereco endereco;
    private boolean ativo = true;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
    }

    public void atualizarDados(@Valid  DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        Optional.ofNullable(dadosAtualizacaoMedico.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAtualizacaoMedico.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAtualizacaoMedico.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAtualizacaoMedico.crm()).ifPresent(s -> this.crm = s);
        Optional.ofNullable(dadosAtualizacaoMedico.especialidade()).ifPresent(s -> this.especialidade = s);
        Optional.ofNullable(dadosAtualizacaoMedico.endereco()).ifPresent(endereco1 ->
                this.endereco.alterarDadosEndereco(dadosAtualizacaoMedico.endereco()));

    }

    public void excluir() {
        this.ativo = false;
    }
}
