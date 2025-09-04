package voll.med2.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import voll.med2.api.domain.medico.Especialidade;
import voll.med2.api.domain.medico.Medico;
import voll.med2.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Entity(name = "Consulta")
@Table(name = "consultas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idconsulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idconsulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_idMedico")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_idPaciente")
    private Paciente paciente;

    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    private LocalDateTime dataConsulta;




}
