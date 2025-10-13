package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med2.api.domain.perfil.DadosPerfil;
import voll.med2.api.domain.usuario.*;

@RestController
public class UsuarioController {


    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<DadosListagemUsuarios> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        var usuario = usuarioService.cadastroUsuario(dadosCadastroUsuario);
        var uri = uriComponentsBuilder.path("/cadastrar/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuarios(usuario));
    }

    @GetMapping("/lista-usuarios")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarUsuariosCadastrados(@PageableDefault(size = 10, sort = {"nome"})
                                                                                 Pageable pageable){
        var page = usuarioService.listarUsuariosCadastrados(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/lista-usuarios/{id}")
    public ResponseEntity<DadosListagemUsuarios> pesquisaPorID(@PathVariable Long id){
        var usuario = usuarioService.pesquisaPorId(id);
        return ResponseEntity.ok(new DadosListagemUsuarios(usuario));
    }

    @GetMapping("/lista-usuarios/{nomeCompleto}")
    public ResponseEntity<DadosListagemUsuarios> pesquisaPorID(@PathVariable String nomeCompleto){
        var usuario = usuarioService.pesquisaPorNome(nomeCompleto);
        return ResponseEntity.ok(new DadosListagemUsuarios(usuario));
    }

    @PutMapping("/atualizar-dados")
    public ResponseEntity<DadosListagemUsuarios> atualizarDadosUsuario(@AuthenticationPrincipal Usuario logado, @RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario){
        var usuario = usuarioService.atualizarNomeUsuario(logado , dadosAtualizacaoUsuario);
        return ResponseEntity.ok(new DadosListagemUsuarios(usuario));
    }

    @PutMapping("/atualizar-senha")
    public ResponseEntity<Void> atualizarSenha(@AuthenticationPrincipal Usuario logado, @RequestBody @Valid
                                               DadosAtualizacaoSenha dadosAtualizacaoSenha){
        usuarioService.atualizarSenha(dadosAtualizacaoSenha, logado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-conta")
    public ResponseEntity<String> validarToken(@RequestParam String codigo){
        usuarioService.validacaoTokenEmail(codigo);
        return ResponseEntity.ok("Conta verificada com sucesso.");
    }

    @PutMapping("/adicionar-perfil/{id}")
    public ResponseEntity<Void> adicionarPerfilUsuario(@PathVariable Long id, DadosPerfil dadosPerfil){
        usuarioService.addPerfilUsuario(id, dadosPerfil);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/inativar-cadastro/{id}")
    public ResponseEntity<Void> inativarCadastro(@PathVariable Long id){
        usuarioService.inativarCadastro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reativar-cadastro/{id}")
    public ResponseEntity<Void> adicionarPerfilUsuario(@PathVariable Long id){
        usuarioService.ativarCadastro(id);
        return ResponseEntity.noContent().build();
    }







}

