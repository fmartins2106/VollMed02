package voll.med2.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voll.med2.api.domain.usuario.DadosAutenticacao2;
import voll.med2.api.domain.usuario.Usuario;

@RestController
@RequestMapping("login")
public class AutenticationController02 {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService2 tokenService2;

    @Autowired
    private ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao2 dadosAutenticacao2){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao2.login(), dadosAutenticacao2.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var tokenJWT = tokenService2.gerarTokenJWT((Usuario) authenticatio/public////////////n.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
