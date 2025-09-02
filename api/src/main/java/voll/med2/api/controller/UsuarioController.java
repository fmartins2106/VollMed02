package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.usuario.AutenticacaoService;
import voll.med2.api.domain.usuario.DadosCadastroUsuario;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        try {
            autenticacaoService.cadastrarUsuario(dados);
            return ResponseEntity.ok().build(); // Retorna 200 OK sem body
        } catch (IllegalArgumentException e) { // caso login já exista
            return ResponseEntity.badRequest().build(); // Retorna 400 Bad Request sem body
        }
    }

}

