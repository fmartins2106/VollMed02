package voll.med2.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long idconsulta,
        Long idmedico,
        Long idpaciente,
        LocalDateTime dataConsulta) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getIdconsulta(), consulta.getMedico().getIdmedico()
        , consulta.getPaciente().getIdpaciente(), consulta.getDataConsulta());
    }

}
