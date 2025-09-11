package voll.med2.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    boolean existsByMedico_IdmedicoAndDataConsultaAndMotivoCancelamentoIsNull(Long idmedico, @NotNull(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future(message = "Data e hora da consulta não pode ser inferior a data de hoje.") LocalDateTime localDateTime);


    boolean existsByPaciente_IdpacienteAndDataConsultaAndMotivoCancelamentoIsNull(Long idpaciente, LocalDateTime dataConsulta);
}
