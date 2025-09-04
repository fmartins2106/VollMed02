package voll.med2.api.domain.paciente;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.consulta.DadosDetalhamentoConsulta;

@Service
public class AgendaDeConsultas {
    public DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        return null;
    }
}
