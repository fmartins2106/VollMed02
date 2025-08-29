package voll.med2.api.infra.seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.usuario.Usuario;

@Service
public class TokenService {

    public String gerarTokenJWT(Usuario usuario){

    }
}
