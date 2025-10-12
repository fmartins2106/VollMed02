package voll.med2.api.infra.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import voll.med2.api.domain.usuario.Usuario;
import voll.med2.api.domain.usuario.UsuarioRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

//O metodo intercepta cada requisição HTTP que chega na aplicação, verifica se existe um token JWT válido no cabeçalho
// (Authorization) e, caso positivo, autentica o usuário no contexto de segurança do Spring. Assim, os próximos filtros,
// controladores e endpoints já "sabem" quem é o usuário e quais são suas permissões, sem precisar pedir login e senha novamente.


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera o token JWT enviado no header "Authorization" da requisição
        var tokenJWT = recuperarToken(request);
        // Apenas para debug: imprime no console o token recebido
        if (tokenJWT != null && !tokenJWT.isBlank()) {
            // Valida o token e obtém o "subject" (login do usuário dentro do token)
            var email = tokenService.getSuject(tokenJWT);
            // Busca o usuário no banco pelo login recuperado do token
            Usuario usuario = usuarioRepository.findByLoginIgnoreCaseAndVerificadoTrue(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado."));
            // Cria um objeto de autenticação do Spring Security com:
            // - principal: o usuário
            // - credentials: null (não precisamos da senha aqui)
            // - authorities: permissões do usuário
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Define a autenticação no contexto do Spring Security
            // Isso permite que endpoints protegidos reconheçam o usuário como autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continua a execução da cadeia de filtros do Spring (não interrompe a requisição)
        filterChain.doFilter(request, response);
    }

// Metodo auxiliar responsável por extrair o token JWT da requisição.
// Ele verifica o header "Authorization" e, caso exista, remove o prefixo "Bearer ",
// retornando apenas o token puro para que possa ser validado posteriormente.
// Se o header não estiver presente, retorna null (sem token).

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
