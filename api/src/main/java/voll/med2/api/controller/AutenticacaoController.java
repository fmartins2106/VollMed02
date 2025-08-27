package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.usuario.DadosAutenticacao;
import voll.med2.api.domain.usuario.Usuario;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(),dadosAutenticacao.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var tokenJTW = tokenServices.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTOkenJTW(tokenJTW));
    }



}
