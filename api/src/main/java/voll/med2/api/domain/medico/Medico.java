package voll.med2.api.domain.medico;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.endereco.DadosEndereco;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    Especialidade especialidade;
    DadosEndereco endereco;
}
