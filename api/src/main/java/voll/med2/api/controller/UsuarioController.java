package voll.med2.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.usuario.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

    /**
     * Endpoint POST /usuarios para cadastro de novos usuários.
     *
     * Fluxo:
     * 1. Recebe os dados do usuário no corpo da requisição (DadosCadastroUsuario).
     * 2. Cria um objeto Usuario usando o DTO recebido e o PasswordEncoder:
     *      - Garante que a senha será armazenada criptografada no banco.
     * 3. Salva o usuário no banco de dados via usuarioRepository.save().
     * 4. Cria a URI do recurso recém-criado para retornar no header "Location".
     * 5. Retorna status HTTP 201 Created, sem expor a senha ou outros dados sensíveis.
     *
     * Objetivo:
     * - Permitir que novos usuários se cadastrem de forma segura.
     * - Integrar com Spring Security para que o usuário possa se autenticar posteriormente.
     */
    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados,UriComponentsBuilder uriBuilder) {
        // Cria o usuário diretamente com o DTO
        var usuario = new Usuario(dados, passwordEncoder);
        // Salva no banco
        usuarioRepository.save(usuario);
        // Cria a URI para o recurso recém-criado
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        // Retorna 201 Created sem expor senha
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizarDadosUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = usuarioRepository.getReferenceById(dados.idusuario());
        usuario.atualizarDadosUsuario(dados);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/email")
    @Transactional
    public ResponseEntity<Void> atualizarEmail(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoEmail dados) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizarEmail(dados.novoEmail(), dados.novaSenha(), passwordEncoder);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/senha")
    @Transactional
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoSenha dados) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizarSenha(dados.novaSenha(), passwordEncoder);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/desbloquear/{id}")
    @Transactional
    public ResponseEntity desbloquear(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desbloquearUsuario();
        return ResponseEntity.ok().build();
    }

    // Desbloqueio com renovação automática das datas
    @PutMapping("/desbloquear-com-renovacao/{id}")
    @Transactional
    public ResponseEntity<Void> desbloquearComRenovacao(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desbloquearUsuarioComRenovacao();
        return ResponseEntity.ok().build();
    }

}

