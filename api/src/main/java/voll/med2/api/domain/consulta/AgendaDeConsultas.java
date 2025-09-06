package voll.med2.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsultas;
import voll.med2.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsultas;
import voll.med2.api.domain.medico.Medico;
import voll.med2.api.domain.medico.MedicoRepository;
import voll.med2.api.domain.paciente.PacienteRepository;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadorAgendamentoConsultas;

    @Autowired
    private List<ValidadorCancelamentoConsultas> validadorCancelamentoConsultas;

    public DadosDetalhamentoConsulta agendarConsulta(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        if (!pacienteRepository.existsById(dadosAgendamentoConsulta.idpaciente())){
            throw new ValidacaoException("ID paciente informado não existe.");
        }

        if (!medicoRepository.existsById(dadosAgendamentoConsulta.idmedico())){
            throw new ValidacaoException("ID médico informato não existe.");
        }

        validadorAgendamentoConsultas.forEach(v -> v.validar(dadosAgendamentoConsulta));
        var paciente = pacienteRepository.findById(dadosAgendamentoConsulta.idpaciente()).get();
        var medico = escolherMedico(dadosAgendamentoConsulta);
        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível.");
        }
        var consulta = new Consulta(null,medico,paciente,dadosAgendamentoConsulta.dataConsulta(),null);

        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idmedico() != null){
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idpaciente());
        }

        if (dadosAgendamentoConsulta == null){
            throw new ValidacaoException("Especialidade é obrigatório quando o médico não for escolhido.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.dataConsulta());
    }

    public void cancelarConsulta(DadosCancelamentoConsulta dadosCancelamentoConsulta){
        if (!consultaRepository.existsById(dadosCancelamentoConsulta.idconsulta())){
            throw new ValidacaoException("Id informado não encontrado. Verique novamente.");
        }
        validadorCancelamentoConsultas.forEach(v -> v.validar(dadosCancelamentoConsulta));
        var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idconsulta());
        consulta.cancelar(dadosCancelamentoConsulta.motivoCancelamento());
    }





}
