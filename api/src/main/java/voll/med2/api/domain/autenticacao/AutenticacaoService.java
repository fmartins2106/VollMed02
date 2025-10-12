package voll.med2.api.domain.autenticacao; // Define o pacote da classe

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails; // Interface que representa um usuário para o Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Interface que define serviço para buscar usuários
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exceção lançada se usuário não for encontrado
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Marca a classe como um Service do Spring (bean gerenciado)
import voll.med2.api.domain.usuario.Usuario;
import voll.med2.api.domain.usuario.UsuarioRepository;
import voll.med2.api.infra.seguranca.TokenService;

@Service // Informa ao Spring que esta classe é um serviço e deve ser gerenciada como bean
public class AutenticacaoService { // Implementa UserDetailsService para autenticação


    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AutenticacaoService(UsuarioRepository usuarioRepository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }




    public DadosTokenJTW autenticacaoUsuario(DadosAutenticacao dadosAutenticacao){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var token = tokenService.gerarTokenJWT((Usuario) authentication.getPrincipal());
        var refreshToken = tokenService.refreshToken((Usuario) authentication.getPrincipal());

        return new DadosTokenJTW(token, refreshToken);
    }


    public DadosTokenJTW atualizarToken(DadosRefreshToken dadosRefreshToken){
        var refreshToken = dadosRefreshToken.refreshToken();
        var usuario = tokenService.getSuject(refreshToken);

        var email = usuarioRepository.findByLogin(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        var token = tokenService.gerarTokenJWT(email);
        var tokenAtualizado = tokenService.refreshToken(email);

        return new DadosTokenJTW(token, tokenAtualizado);
    }





}
