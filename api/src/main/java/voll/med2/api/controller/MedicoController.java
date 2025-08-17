package voll.med2.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.medico.*;
import voll.med2.api.domain.paciente.DadosDetalhamentoPaciente;
import voll.med2.api.domain.paciente.Paciente;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity salvar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        var uri = uriComponentsBuilder.path("/medicos{id}/").buildAndExpand(medico.getIdMedico()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listagemMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        var page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page);
    }

}
