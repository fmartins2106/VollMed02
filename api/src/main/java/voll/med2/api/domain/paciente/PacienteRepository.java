package voll.med2.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findAllByAtivoTrue(Pageable pageable);


    @Query("""
            select
            p.ativo
            from Paciente p
            where p.idpaciente = :idpaciente and p.ativo = true
            """)
    boolean findAtivoById(@NotNull(message = "Erro. Necessário informar o id do paciente.") Long idpaciente);


    @Query("""
            select
            m
            from Paciente p
            where p.idpaciente = :idpaciente and p.ativo = true;
            """)
    Optional<Paciente> findByIdPacienteAtivo(@NotNull(message = "Erro. Necessário informar o id do paciente.") Long idpaciente);
}
