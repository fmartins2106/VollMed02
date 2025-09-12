package voll.med2.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.ConsultaRepository;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

@Component()
public class ValidadorHOrarioFuncionamentoClinica implements ValidadorAgendamentoConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var dataConsulta = dadosAgendamentoConsulta.dataConsulta();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var sabado = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
        if (domingo || sabado || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("COnsulta fora do horário de funcionamento da cliníca.");
        }
    }







}
