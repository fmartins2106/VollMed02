package voll.med2.api.infra.seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.usuario.Usuario02;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class TokenService02 {


    @Value("${api.security.token.secret}")
    private String secret;


    public String gerarTokenJWT(Usuario02 usuario02){
        System.out.println(secret);
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Med_vell.")
                    .withSubject(usuario02.getLogin())
                    .withExpiresAt(tempoExpiracao())
                    .sign(algoritimo);
        }catch (JWTCreationException exception){
            throw new  RuntimeException("Erro ao gerar login.",exception);
        }
    }

    private Instant tempoExpiracao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .plusHours(6)
                .toInstant();
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API Med Voll.")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Erro de validação do token JWT ou token expirado.");
        }
    }
}
