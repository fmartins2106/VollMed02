package voll.med2.api.domain.paciente;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Endereco endereco;
    private boolean ativo;
}
