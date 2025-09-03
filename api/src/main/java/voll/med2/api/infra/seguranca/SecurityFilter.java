package voll.med2.api.infra.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import voll.med2.api.domain.usuario.UsuarioRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera o token JWT enviado no header "Authorization" da requisição
        var tokenJWT = recuperarToken(request);
        // Apenas para debug: imprime no console o token recebido
        if (tokenJWT != null && !tokenJWT.isBlank()) {
            // Valida o token e obtém o "subject" (login do usuário dentro do token)
            var subject = tokenService.getSuject(tokenJWT);
            // Busca o usuário no banco pelo login recuperado do token
            var usuario = usuarioRepository.findByLogin(subject);
            // Cria um objeto de autenticação do Spring Security com:
            // - principal: o usuário
            // - credentials: null (não precisamos da senha aqui)
            // - authorities: permissões do usuário
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
            // Define a autenticação no contexto do Spring Security
            // Isso permite que endpoints protegidos reconheçam o usuário como autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continua a execução da cadeia de filtros do Spring (não interrompe a requisição)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar que pega o token JWT do header "Authorization"
    private String recuperarToken(HttpServletRequest request) {
        // Lê o header "Authorization" da requisição
        var authorizationHeader = request.getHeader("Authorization");
        // Se existir, remove o prefixo "Bearer " e retorna apenas o token
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        // Se não houver token, retorna null
        return null;
    }








}
