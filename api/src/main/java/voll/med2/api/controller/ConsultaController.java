package voll.med2.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voll.med2.api.domain.consulta.AgendaDeConsultas;
import voll.med2.api.domain.consulta.DadosAgendamentoConsulta;
import voll.med2.api.domain.consulta.DadosCancelamentoConsulta;
import voll.med2.api.domain.consulta.DadosListagemConsultas;
import voll.med2.api.domain.usuario.Usuario;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "Bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var consulta = agendaDeConsultas.agendarConsulta(dadosAgendamentoConsulta);
        return ResponseEntity.ok(consulta);
    }

    @DeleteMapping
    public ResponseEntity cancelamentoConsulta(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamentoConsulta){
        agendaDeConsultas.cancelarConsulta(dadosCancelamentoConsulta);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsultas>> listarConsulas(@PageableDefault(size = 10, sort = {"nome"})
                                                                       Pageable pageable, Usuario logado){
        var page = agendaDeConsultas.listarConsultas(pageable, logado);
        return ResponseEntity.ok(page);
    }

}
