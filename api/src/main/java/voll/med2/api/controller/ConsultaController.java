package voll.med2.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voll.med2.api.domain.consulta.AgendaDeConsultas;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.consulta.DadosCancelamentoConsulta;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "Bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var consulta = agendaDeConsultas.agendarConsulta(dadosAgendamentoConsulta);
        return ResponseEntity.ok(consulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta){
        agendaDeConsultas.cancelarConsulta(dadosCancelamentoConsulta);
        return ResponseEntity.noContent().build();
    }



}
