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
public class AutenticacaoService implements UserDetailsService { // Implementa UserDetailsService para autenticação


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Implementação do metodo da interface UserDetailsService.
     *
     * Funcionalidade:
     * - Recebe um username (login) e busca o usuário correspondente no banco.
     * - Se o usuário existir, retorna um objeto que implementa UserDetails, contendo:
     *      • login
     *      • senha (hash)
     *      • roles/permissões
     * - Se não existir, lança UsernameNotFoundException (Spring Security trata adequadamente).
     *
     * Objetivo:
     * Permitir que o Spring Security consiga autenticar e autorizar o usuário
     * baseado nos dados carregados do banco.
     */
    @Override // Sobrescreve o metodo da interface UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo login no repositório e lança exceção se não existir
        return usuarioRepository.findByLogin(username)               // Consulta o usuário no banco
                .orElseThrow(() -> new UsernameNotFoundException(    // Se não existir, lança a exceção adequada
                        "Usuário não encontrado: " + username));
        // Retorna o usuário encontrado, que implementa UserDetails
    }

    @Transactional
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

        var email = usuarioRepository.FindByEmail(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        var token = tokenService.gerarTokenJWT(email);
        var tokenAtualizado = tokenService.refreshToken(email);

        return new DadosTokenJTW(token, tokenAtualizado);
    }





}
