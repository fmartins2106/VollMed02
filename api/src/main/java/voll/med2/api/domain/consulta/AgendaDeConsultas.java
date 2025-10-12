package voll.med2.api.domain.consulta;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsultas;
import voll.med2.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsultas;
import voll.med2.api.domain.medico.Medico;
import voll.med2.api.domain.medico.MedicoRepository;
import voll.med2.api.domain.paciente.PacienteRepository;
import voll.med2.api.domain.usuario.Usuario;
import voll.med2.api.infra.seguranca.HierarquiaService;

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

    @Autowired
    private HierarquiaService hierarquiaService;

    @Transactional
    public DadosDetalhamentoConsulta agendarConsulta(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        // Verifica se o paciente informado existe no banco de dados
        if (!pacienteRepository.existsById(dadosAgendamentoConsulta.idpaciente())){
            throw new ValidacaoException("ID paciente informado não existe.");
        }
        if (dadosAgendamentoConsulta.idmedico() != null && !medicoRepository.existsById(dadosAgendamentoConsulta.idmedico())) {
            throw new ValidacaoException("Id do medico informado não existe.");
        }
        // Recupera o paciente do banco pelo ID (já validado anteriormente)
        var paciente = pacienteRepository.findById(dadosAgendamentoConsulta.idpaciente()).get();
        // Escolhe o médico que atenderá a consulta (pode ser baseado em regras de disponibilidade, especialidade, etc.)
        var medico = escolherMedico(dadosAgendamentoConsulta);
        // Caso não seja possível selecionar um médico disponível, lança uma exceção
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível.");
        }
        // Executa uma lista de validadores customizados para checar regras de negócio
        validadorAgendamentoConsultas.forEach(v -> v.validar(dadosAgendamentoConsulta));
        // Cria uma nova entidade "Consulta" preenchendo:
        // - ID nulo (será gerado automaticamente pelo banco)
        // - médico escolhido
        // - paciente encontrado
        // - data da consulta informada
        // - campo adicional (como motivo, observações) ainda nulo
        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.dataConsulta(), null);
        // Persiste a consulta no banco de dados
        consultaRepository.save(consulta);
        // Retorna os dados detalhados da consulta agendada (DTO de resposta para a camada externa)
        return new DadosDetalhamentoConsulta(consulta);
    }

    @Transactional
    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        // Caso o ID de um médico tenha sido informado diretamente na requisição,
        // retorna esse médico sem precisar aplicar regras adicionais
        if (dadosAgendamentoConsulta.idmedico() != null){
            // Verifica se o médico informado existe no banco de dados
            if (!medicoRepository.existsById(dadosAgendamentoConsulta.idmedico())){
                throw new ValidacaoException("ID médico informato não existe.");
            }
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idmedico());
        }
        // Se o médico não foi informado, mas também não veio a especialidade,
        // não é possível escolher automaticamente: lança uma exceção de validação
        if (dadosAgendamentoConsulta.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatório quando o médico não for escolhido.");
        }
        // Caso o médico não tenha sido informado, mas a especialidade foi,
        // o sistema busca no banco um médico disponível de forma aleatória
        // que atenda a especialidade informada e esteja livre na data/hora da consulta
        return medicoRepository.escolherMedicoAleatorioLivreNaData(
                dadosAgendamentoConsulta.especialidade(),
                dadosAgendamentoConsulta.dataConsulta()
        );
    }

    @Transactional
    public void cancelarConsulta(DadosCancelamentoConsulta dadosCancelamentoConsulta){
        // Verifica se a consulta informada existe no banco de dados
        if (!consultaRepository.existsById(dadosCancelamentoConsulta.idconsulta())){
            throw new ValidacaoException("Id informado não encontrado. Verique novamente.");
        }
        // Executa todos os validadores de cancelamento para aplicar regras de negócio,
        // como: prazos de cancelamento, restrições do paciente, etc.
        validadorCancelamentoConsultas.forEach(v -> v.validar(dadosCancelamentoConsulta));
        // Recupera a consulta do banco de dados usando o ID informado
        var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idconsulta());
        // Invoca o metodo da entidade "Consulta" para realizar o cancelamento,
        // passando o motivo do cancelamento como parâmetro
        consulta.cancelar(dadosCancelamentoConsulta.motivoCancelamento());
    }

    public Page<DadosListagemConsultas> listarConsultas(Pageable pageable, Usuario logado) {
        if (hierarquiaService.usuarioTemPermissao(logado, "MEDICO")) {
            // Consulta apenas do médico logado
            return consultaRepository.findByMedicoIdAndMotivoCancelamentoIsNull(logado.getId(), pageable)
                    .map(DadosListagemConsultas::new);
        }
        // Consulta todas as consultas não canceladas
        return consultaRepository.findByMotivoCancelamentoIsNull(pageable)
                .map(DadosListagemConsultas::new);
    }








}
