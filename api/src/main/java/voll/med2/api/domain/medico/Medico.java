package voll.med2.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.Endereco;

@Entity(name = "Medico")
@Table(name = "medico")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    @NotBlank
    private boolean ativo = true;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
    }
}
