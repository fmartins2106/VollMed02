package voll.med2.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idmedico() == null){
            return;
        }
        var medicoEstaAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idmedico());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada, médico inativo.");
        }
    }



}
