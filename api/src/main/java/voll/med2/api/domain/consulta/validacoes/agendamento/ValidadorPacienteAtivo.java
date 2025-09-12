package voll.med2.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var pacienteAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idpaciente());

        if (!pacienteAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo.");
        }
    }



}
