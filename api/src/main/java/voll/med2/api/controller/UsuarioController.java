package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.usuario.DadosCadastroUsuario;
import voll.med2.api.domain.usuario.Usuario;
import voll.med2.api.domain.usuario.UsuarioRepository;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados,UriComponentsBuilder uriBuilder) {
        // Cria o usuário diretamente com o DTO
        var usuario = new Usuario(dados, passwordEncoder);
        // Salva no banco
        usuarioRepository.save(usuario);
        // Cria a URI para o recurso recém-criado
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getIdusuario()).toUri();
        // Retorna 201 Created sem expor senha
        return ResponseEntity.created(uri).build();
    }







}

