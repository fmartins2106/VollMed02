package voll.med2.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import voll.med2.api.domain.consulta.Consulta;
import voll.med2.api.domain.paciente.DadosCadastroPaciente;
import voll.med2.api.domain.paciente.Paciente;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class MedicoRepositoryTest02 {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deveria devolver nulo quando o único médico cadastrado não está disponível.")
    void escolherMedicoAleatorioLivreNaData() {
        var consultaAgendadaParaDia10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastroMedico("Medico01","medico@medico.com","123123", Especialidade.CARDIOLOGIA);
        var paciente = cadastroPaciente("Paciente01","paciente01@gmail.com","123123123");
        var consulta = new Consulta(null, medico, paciente, consultaAgendadaParaDia10,null);
    }

    private Paciente cadastroPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome,email, cpf));
        testEntityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
    }

    private Medico cadastroMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        testEntityManager.persist(medico);
        return
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    }
}