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
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.medico.*;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "Bearer-key")
public class DadosMedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        var uri = uriComponentsBuilder.path("/medico/{idmedico}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listagemDadosMedicos(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable){
        var page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDadosMedico(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico){
        var medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.idmedico());
        medico.atualizarDados(dadosAtualizacaoMedico);
        return ResponseEntity.ok().body(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("{idmedico}")
    @Transactional
    public ResponseEntity deletarDadosMedico(@PathVariable Long idmedico){
        var medico = medicoRepository.getReferenceById(idmedico);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

}

