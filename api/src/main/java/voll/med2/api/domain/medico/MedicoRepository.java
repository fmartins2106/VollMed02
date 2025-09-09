package voll.med2.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);


    @Query("""
            select
            m from Medico m
            where m.ativo = true
            and m.especialidade = :especialidade
            and m.idmedico not in(
                select
                c.idmedico
                from Consulta c
                where c.dataHoraConsulta = :dataHoraConsulta
                and c.motivoCancelamento is null
            )
            order by random() limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(@NotNull Especialidade especialidade, @NotBlank(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future LocalDateTime data);



    @Query("""
            select
            .ativo
            from Medico m
            where m.id = :id
            """)
    Boolean findAtivoById(Long idmedico);
}
