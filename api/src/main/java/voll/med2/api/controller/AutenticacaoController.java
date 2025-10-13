package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voll.med2.api.domain.autenticacao.AutenticacaoService;
import voll.med2.api.domain.autenticacao.DadosRefreshToken;
import voll.med2.api.domain.autenticacao.DadosAutenticacao;
import voll.med2.api.domain.autenticacao.DadosTokenJTW;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

   private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }


    /**
     * Endpoint POST /login para autenticação de usuários.
     *
     * Fluxo:
     * 1. Recebe login e senha do usuário no corpo da requisição (DadosAutenticacao).
     * 2. Cria um UsernamePasswordAuthenticationToken com as credenciais.
     * 3. Chama o AuthenticationManager para autenticar o usuário:
     *      - Se inválido → lança exceção e retorna erro.
     *      - Se válido → retorna Authentication com o usuário autenticado.
     * 4. Gera um token JWT para o usuário autenticado usando tokenService.gerarTokenJWT().
     * 5. Retorna o token JWT dentro de um objeto DadosTokenJWT para o cliente.
     *
     * Objetivo:
     * - Permitir que o cliente receba um token JWT após login bem-sucedido.
     * - Esse token será usado nas requisições seguintes para autenticação.
     */
    @PostMapping
    public ResponseEntity<DadosTokenJTW> efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        var token = autenticacaoService.autenticacaoUsuario(dadosAutenticacao);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/atualizar-token")
    public ResponseEntity<DadosTokenJTW> atualizarLogin(@RequestBody @Valid DadosRefreshToken dadosRefreshToken){
        var tokenAtualizado = autenticacaoService.atualizarToken(dadosRefreshToken);
        return ResponseEntity.ok(tokenAtualizado);
    }
}
