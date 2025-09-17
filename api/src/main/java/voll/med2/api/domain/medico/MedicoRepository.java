package voll.med2.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);


    @Query("""
            select m 
            from Medico m
            where m.ativo = true
            and m.especialidade = :especialidade
            and m.idmedico not in (
                select 
                c.medico.id
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
    boolean findAtivoById(Long idmedico);




    @Query("""
            select m
            from Medico m
            where m.ativo = true
            and m.especialidade = :especialidade
            and m.idmedico = :idmedico not in(
            select
            c.medico.idmedico
            from Consulta c
            where c.dataConsulta = :dataConsulta
            and c.motivoCancelamento is null
            ) order by Random() limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData02(@NotNull(message = "Campo especialidade não pode ser vazio.") Especialidade especialidade, @NotNull(message = "Campo data consulta não pode ser vazio.") @Future(message = "Data da consulta não pode ser inferior a data de hoje.") LocalDateTime localDateTime);


    @Query("""
            select
            m.ativo
            from Medico m
            where m.ativo = true and m.idmedico = :idmedico
            """)
    boolean findAtivoById02(Long idmedico);
}
