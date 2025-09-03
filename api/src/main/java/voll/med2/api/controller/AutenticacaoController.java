package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.usuario.DadosAutenticacao;
import voll.med2.api.domain.usuario.Usuario;
import voll.med2.api.infra.seguranca.DadosTokenJTW;
import voll.med2.api.infra.seguranca.TokenService;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


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
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var TokenJWT = tokenService.gerarTokenJWT((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJTW(TokenJWT));
    }

}
