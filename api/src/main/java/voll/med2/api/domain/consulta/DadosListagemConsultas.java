package voll.med2.api.domain.consulta;

import voll.med2.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosListagemConsultas(
        Long idconsulta,
        Long idmedico,
        Long idpaciente,
        LocalDateTime dataConsulta,
        Especialidade especialidade) {


    public DadosListagemConsultas(Consulta consulta) {
        this(consulta.getIdconsulta(), consulta.getMedico().getId()
                , consulta.getPaciente().getIdpaciente(), consulta.getDataConsulta(),consulta.getMedico().getEspecialidade());
    }
}
