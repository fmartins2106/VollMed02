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
import voll.med2.api.domain.pacientes.*;

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
        var uri = uriComponentsBuilder.path("/pacientes{idpaciente}/").buildAndExpand(dadosCadastroPaciente).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacientes>> listagemPacientesCadastrados(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable){
        var page = pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDadosPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente){
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.idpaciente());
        paciente.atualizarDados(dadosAtualizacaoPaciente);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{idpaciente}")
    @Transactional
    public ResponseEntity excluirDadosPaciente(@PathVariable Long idpaciente){
        var paciente = pacienteRepository.getReferenceById(idpaciente);
        paciente.excluir(paciente);
        return ResponseEntity.noContent().build();
    }

}
