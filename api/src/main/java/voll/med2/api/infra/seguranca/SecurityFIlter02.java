package voll.med2.api.infra.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import voll.med2.api.domain.usuario.UsuarioRepository;
import voll.med2.api.domain.usuario.UsuarioRepository02;

import java.io.IOException;

@Component
public class SecurityFIlter02 extends OncePerRequestFilter {

    @Autowired
    private TokenService02 tokenService02;

    @Autowired
    private UsuarioRepository02 usuarioRepository02;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT.isBlank()){
            var subject = tokenService02.getSubject(tokenJWT);
            var usuario = usuarioRepository02.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizaronHeader = request.getHeader("authorization");
        if (authorizaronHeader != null){
            return authorizaronHeader.replace("Bearer ","");
        }
        return null;
    }
}
