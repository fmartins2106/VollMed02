package voll.med2.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("medicos")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;
}
