package voll.med2.api.domain.consulta.validacoes.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.consulta.ConsultaRepository;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.consulta.DadosCancelamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCancelamentoConsulta dadosCancelamentoConsulta){
        var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idconsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getDataConsulta()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }


}
