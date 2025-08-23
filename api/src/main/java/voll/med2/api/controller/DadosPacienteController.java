package voll.med2.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.paciente.*;

@RestController
@RequestMapping("pacientes")
public class DadosPacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente, UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(dadosCadastroPaciente);
        pacienteRepository.save(paciente);
        var uri = uriComponentsBuilder.path("/paciente{idpaciente}").buildAndExpand(paciente.getIdpaciente()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listagemPacientesCadastradosAtivos(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable){
        var page = pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterarDadosPaciente(@RequestBody @Valid DadosAtualizacaoPacientes dadosAtualizacaoPacientes){
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPacientes.idpaciente());
        paciente.atualizarDados(dadosAtualizacaoPacientes);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{idpaciente}")
    @Transactional
    public ResponseEntity excluirDadosPaciente(@PathVariable Long idpaciente){
        var paciente = pacienteRepository.getReferenceById(idpaciente);
        paciente.excluirDados();
        return ResponseEntity.noContent().build();
    }
}
