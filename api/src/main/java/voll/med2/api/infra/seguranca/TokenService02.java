package voll.med2.api.infra.seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.usuario.Usuario;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TokenService02 {


    @Value("${api.security.token.secret}")
    private String secret;


    public String gerarToken(Usuario usuario){
        System.out.println(secret);
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Med_voll")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(tempoExpiracao())
                    .sign(algoritimo);
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar TokenJWT.",exception);
        }
    }

    public Instant tempoExpiracao(){
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .plusHours(6)
                .toInstant();
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API Med_voll")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Erro, token expirado ou inválido.");
        }
    }




}
