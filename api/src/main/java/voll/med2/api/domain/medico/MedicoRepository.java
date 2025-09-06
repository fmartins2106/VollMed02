package voll.med2.api.domain.medico;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    Medico escolherMedicoAleatorioLivreNaData(@NotNull Especialidade especialidade, @NotBlank(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future LocalDateTime data);
}
