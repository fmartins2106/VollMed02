package voll.med2.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.Endereco;
@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;
    private boolean ativo = true;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.dadosEndereco());
    }
}
