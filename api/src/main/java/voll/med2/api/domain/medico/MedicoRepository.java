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
import java.util.Optional;

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



}
