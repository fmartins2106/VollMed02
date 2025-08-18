package voll.med2.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private Endereco endereco;

    @NotNull
    private boolean ativo = true;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
        this.ativo = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedicos dadosAtualizacaoMedicos) {
        Optional.ofNullable(dadosAtualizacaoMedicos.nome()).ifPresent(n -> this.nome = n);
        Optional.ofNullable(dadosAtualizacaoMedicos.email()).ifPresent(e -> this.email = e);
        Optional.ofNullable(dadosAtualizacaoMedicos.telefone()).ifPresent(t -> this.telefone = t);
        Optional.ofNullable(dadosAtualizacaoMedicos.endereco()).ifPresent(endereco1 ->
                this.endereco.atualizarInformacoes(endereco1));
    }
}
