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
            select m from Medico m
            where m.ativo = true
            and m.especialidade = :especialidade
            and m.idmedico not in (
                select c.medico.id
                from Consulta c
                where c.dataConsulta = :dataConsulta
            and c.motivoCancelamento is null
            )
            order by random() limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(@NotNull Especialidade especialidade, @NotBlank(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future LocalDateTime dataConsulta);



    @Query("""
            select
            m.ativo
            from Medico m
            where m.idmedico = :idmedico and m.ativo = true
            """)
    Boolean findAtivoById(Long idmedico);


    @Query("""
            select
            m.ativo
            from Medico M
            where m.ativo = true
            and m.especialidade = :especialidade
            and m.idmedico  not in (
            select
            c.medico.idmedico
            from consultas c
            where c.dataConsulta = :dataConsulta and c.motivoCancelamento is not null
            ) order by Random() limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData02(Long idmedico, @NotNull(message = "Erro campo data da consulta não pode ser vazio  ou conter data inferior a data de hoje.") @Future(message = "Data e hora da consulta não pode ser inferior a data de hoje.") LocalDateTime localDateTime);
}
