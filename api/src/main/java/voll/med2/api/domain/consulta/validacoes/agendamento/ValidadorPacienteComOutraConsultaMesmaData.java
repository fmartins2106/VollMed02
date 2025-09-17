package voll.med2.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.ConsultaRepository;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteComOutraConsultaMesmaData implements ValidadorAgendamentoConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var idpaciente = dadosAgendamentoConsulta.idpaciente();
        var dataConsulta = dadosAgendamentoConsulta.dataConsulta();
        var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPaciente_IdpacienteAndDataConsultaAndMotivoCancelamentoIsNull(idpaciente, dataConsulta);
        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia.");
        }
    }



}
