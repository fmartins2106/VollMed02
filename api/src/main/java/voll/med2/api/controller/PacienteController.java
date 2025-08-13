package voll.med2.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.paciente.DadosCadastroPaciente;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroPaciente dadosCadastroPaciente){
        System.out.println("Dados recebidos:"+dadosCadastroPaciente);
    }

}
