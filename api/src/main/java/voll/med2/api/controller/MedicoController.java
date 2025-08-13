package voll.med2.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.medico.DadosCadastroMedico;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @PostMapping
    public ResponseEntity cadastrar(DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriComponentsBuilder){

    }


}
