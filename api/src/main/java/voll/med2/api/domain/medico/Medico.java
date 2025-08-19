package voll.med2.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.Endereco;

import javax.swing.text.html.Option;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;
    private boolean ativo = true;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.dadosEndereco());
    }

    public void atualizarDadosMedico(DadosAtualizacaoMedicos dadosAtualizacaoMedicos) {
        Optional.ofNullable(dadosAtualizacaoMedicos.nome()).ifPresent(s -> this.nome = s);
        Optional.ofNullable(dadosAtualizacaoMedicos.email()).ifPresent(s -> this.email = s);
        Optional.ofNullable(dadosAtualizacaoMedicos.telefone()).ifPresent(s -> this.telefone = s);
        Optional.ofNullable(dadosAtualizacaoMedicos.crm()).ifPresent(s -> this.crm = s);
        Optional.ofNullable(dadosAtualizacaoMedicos.especialidade()).ifPresent(s -> this.especialidade = s);
        Optional.ofNullable(dadosAtualizacaoMedicos.endereco()).ifPresent(s -> this.endereco = s);
    }

    public void excluir(Medico medico) {
        this.ativo = false;
    }
}
