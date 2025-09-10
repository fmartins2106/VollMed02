package voll.med2.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.ConsultaRepository;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var medicoPossuiOutraConsultaMesmoHorario = consultaRepository.existsByMedico_IdmedicoAndDataHoraConsultaAndMotivoCancelamentoIsNull(dadosAgendamentoConsulta.idmedico(), dadosAgendamentoConsulta.dataConsulta());
        if (medicoPossuiOutraConsultaMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta agendada neste mesmo horário.");
        }
    }



}
