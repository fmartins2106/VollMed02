package voll.med2.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import voll.med2.api.domain.consulta.Consulta;
import voll.med2.api.domain.endereco.DadosEndereco;
import voll.med2.api.domain.paciente.DadosCadastroPaciente;
import voll.med2.api.domain.paciente.Paciente;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// Cria um "slice test" focado no JPA (persistência).
// Sobe apenas o necessário para testar repositórios (EntityManager, DataSource, etc.).
// Não carrega o contexto inteiro da aplicação (como controllers, services).

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Por padrão, o @DataJpaTest troca seu banco por um H2 em memória.
// Essa anotação diz: "Não troque, use o banco real configurado (ex: Postgres, MySQL)".
// Útil quando você quer rodar os testes contra o mesmo banco do ambiente de dev/test.

@ActiveProfiles("test")
// Ativa o profile "test" definido no application-test.properties (ou .yml).
// Assim você pode ter configurações específicas para os testes
// (como usar outro banco, porta diferente, logs mais simples, etc.).

class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager; // Utilitário de teste do JPA para persistir, buscar e manipular entidades rapidamente no banco durante @DataJpaTest

    @Test
    @DisplayName("Deveria devolver null quando o único médico cadastrado não está disponível na data.")
    void escolherMedicoAleatorioLivreNaDataCenario01() {
        // Cria uma data: próxima segunda-feira às 10h
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        // Cadastra um médico no banco de teste
        var medico = cadastrarMedico("Medico01", "medico01@medvoll.com.br", "123123", Especialidade.CARDIOLOGIA);

        // Cadastra um paciente no banco de teste
        var paciente = cadastrarPaciente("Paciente01","paciente01@medvoll.com.br","999999");

        // Agenda uma consulta para o médico e paciente na data criada
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        // Tenta buscar um médico livre para aquela especialidade e data
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // Verifica se o resultado é null (porque o único médico já está ocupado)
        assertThat(medicoLivre).isNull();
    }


    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data.")
    void escolherMedicoAleatorioLivreNaDataCenario02() {
        // Cria a mesma data: próxima segunda-feira às 10h
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        // Cadastra um médico no banco de teste
        var medico = cadastrarMedico("Medico01", "medico01@medvoll.com.br", "123123", Especialidade.CARDIOLOGIA);

        // Busca um médico livre para aquela especialidade e data
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // Verifica se o médico retornado é o mesmo que foi cadastrado (ou seja, está livre)
        assertThat(medicoLivre).isEqualTo(medico);
    }


    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime proximaSegundaAs10) {
        // Persiste uma nova consulta no banco de teste
        testEntityManager.persist(new Consulta(null, medico, paciente, proximaSegundaAs10,null));
    }


    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        // Cria um objeto DadosCadastroMedico, depois instancia um Medico e persiste
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        testEntityManager.persist(medico);
        return medico; // Retorna o médico cadastrado
    }


    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        // Cria um objeto DadosCadastroPaciente, depois instancia um Paciente e persiste
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);
        return paciente; // Retorna o paciente cadastrado
    }


    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        // Cria um DTO (DadosCadastroMedico) com nome, email, crm, especialidade e endereço
        return new DadosCadastroMedico(
                nome,
                email,
                "2020202020",       // telefone fictício
                crm,
                especialidade,
                dadosEndereco()     // chama método que cria endereço
        );
    }


    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        // Cria um DTO (DadosCadastroPaciente) com nome, email, cpf e endereço
        return new DadosCadastroPaciente(
                nome,
                email,
                "220202",           // telefone fictício
                cpf,
                dadosEndereco()     // chama método que cria endereço
        );
    }


    private @NotNull DadosEndereco dadosEndereco() {
        // Cria um DTO de endereço com valores fictícios
        return new DadosEndereco(
                "rua",
                "das flores",
                null,
                null,
                "0000000",    // CEP fictício
                "centro",
                "Blumenau",
                "SC");
    }



}