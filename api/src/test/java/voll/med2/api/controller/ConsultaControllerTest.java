package voll.med2.api.controller;

import jakarta.validation.constraints.NotNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import voll.med2.api.domain.consulta.*;
import voll.med2.api.domain.endereco.DadosEndereco;
import voll.med2.api.domain.endereco.Endereco;
import voll.med2.api.domain.medico.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @Autowired
    private JacksonTester<DadosCancelamentoConsulta> dadosCancelamentoConsultaJacksonTester;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;



    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas.")
    @WithMockUser
    void agendar_cenario01() throws Exception {
        var response = mockMvc.perform(post("/medicos"))
                .andReturn().getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria devolver o codigo http 200 quando informações estáo inválidas.")
    void agendar_cenario2() throws Exception{
        var data = LocalDateTime.now().plusHours(2);
        var especialidade = Especialidade.CARDIOLOGIA;

            var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data,especialidade);
        when(agendaDeConsultas.agendarConsulta(any()))
                .thenReturn(dadosDetalhamento);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJacksonTester
                                .write(new DadosAgendamentoConsulta(21L,51L,data, especialidade)).getJson()))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJacksonTester.
                write(dadosDetalhamento).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria devolver código 204 quando o cancelamento for válido")
    void cancelarConsulta_cenarioValido() throws Exception {
        // 1. Preparar os dados de cancelamento
        var dataCancelamento = LocalDateTime.now();
        var dadosCancelamento = new DadosCancelamentoConsulta(10L, dataCancelamento,MotivoCancelamento.MEDICO_CANCELOU);

        // 2. Executar a requisição DELETE
        var response = mockMvc.perform(delete("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCancelamentoConsultaJacksonTester.write(dadosCancelamento).getJson()))
                .andReturn()
                .getResponse();

        // 3. Validar o status da resposta
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

        // 4. Verificar se o service foi chamado (opcional)
        verify(agendaDeConsultas).cancelarConsulta(any(DadosCancelamentoConsulta.class));
    }


}