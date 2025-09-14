package voll.med2.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.usuario.DadosAutenticacao02;
import voll.med2.api.domain.usuario.Usuario02;
import voll.med2.api.infra.seguranca.DadosTokenJWT02;
import voll.med2.api.infra.seguranca.TokenService02;
import voll.med2.api.s.DadosTokenJWT;

@RestController
@RequestMapping("login")
public class AutenticacaoController02 {

    @Autowired
    private TokenService02 tokenService02;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity efetuarLogin(DadosAutenticacao02 dadosAutenticacao02){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao02.login(), dadosAutenticacao02.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService02.gerarTokenJWT((Usuario02) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT02(tokenJWT));
    }

}
