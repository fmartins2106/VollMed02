package voll.med2.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import voll.med2.api.domain.endereco.DadosEndereco;

@Entity(name = "Medico")
@Table(name = "medicos")
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
    Especialidade especialidade;

    @Embedded // Indica que os atributos de Endereco serão colunas desta tabela.
    DadosEndereco endereco;

    @NotNull
    private boolean ativo;
}
