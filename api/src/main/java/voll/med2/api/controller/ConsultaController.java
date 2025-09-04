package voll.med2.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.paciente.AgendaDeConsultas;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    @Transactional
    public RestController angendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var consulta = agendaDeConsultas.agendar(dadosAgendamentoConsulta);
        return ResponseEntity.ok(consulta);
    }

}
