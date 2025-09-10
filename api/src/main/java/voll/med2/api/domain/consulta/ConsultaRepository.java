package voll.med2.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    boolean existsByMedico_IdmedicoAndDataHoraConsultaAndMotivoCancelamentoIsNull(Long idmedico, @NotNull(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future(message = "Data e hora da consulta não pode ser inferior a data de hoje.") LocalDateTime localDateTime);

    boolean existsByPacienteIdAndDataBetween(@NotNull(message = "Erro. Necessário informar o id do paciente.") Long idpaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
