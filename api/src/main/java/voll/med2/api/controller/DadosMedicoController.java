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
import voll.med2.api.domain.medico.*;

@RestController
@RequestMapping("medicos")
public class DadosMedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity salvarCadastroMedico(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        var uri = uriComponentsBuilder.path("/medicos/{idmedico}").buildAndExpand(medico.getIdmedico()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listagemDadosMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        var page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDadosMedicos(@RequestBody @Valid DadosAtualizacaoMedicos dadosAtualizacaoMedicos){
        var medico = medicoRepository.getReferenceById(dadosAtualizacaoMedicos.idmedico());
        medico.atualizarDadosMedico(dadosAtualizacaoMedicos);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{idmedico}")
    @Transactional
    public ResponseEntity excluirDadosMedicos(@PathVariable Long idmedico){
        var medico = medicoRepository.getReferenceById(idmedico);
        medico.excluir(medico);
        return ResponseEntity.ok(idmedico);
    }
}
