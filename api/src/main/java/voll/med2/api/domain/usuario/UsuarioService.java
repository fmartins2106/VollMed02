package voll.med2.api.domain.usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.perfil.DadosPerfil;
import voll.med2.api.domain.perfil.PerfilRepository;
import voll.med2.api.domain.perfil.Perfilnome;
import voll.med2.api.infra.email.EmailService;

@Service
public class UsuarioService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final EmailService emailService;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.emailService = emailService;
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
    public Usuario cadastroUsuario(DadosCadastroUsuario dadosCadastroUsuario){
        var senhaCriptografada = passwordEncoder.encode(dadosCadastroUsuario.senha());
        var perfil = perfilRepository.findByPerfilNome(Perfilnome.PACIENTE);
        var usuario = new Usuario(dadosCadastroUsuario, senhaCriptografada, perfil);
        usuarioRepository.save(usuario);
        emailService.enviarEmailVerificacao(usuario);
        return usuario;
    }


    public Page<DadosListagemUsuarios> listarUsuariosCadastrados(Pageable pageable){
        return usuarioRepository.findByAtivoTrue(pageable).map(DadosListagemUsuarios::new);
    }


    public Usuario pesquisaPorNome(String nomeCompleto){
        return usuarioRepository.findByNomeCompleto(nomeCompleto)
                .orElseThrow(() -> new RuntimeException("Nome não encontrado."));
    }


    public Usuario pesquisaPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID não encontrado. Tente novamente."));
    }

    @Transactional
    public Usuario atualizarNomeUsuario(Usuario usuario, DadosAtualizacaoUsuario dadosAtualizacaoUsuario){
        usuario.atualizarNome(dadosAtualizacaoUsuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarSenha(DadosAtualizacaoSenha dadosAtualizacaoSenha, Usuario usuario){
        if (!passwordEncoder.matches(dadosAtualizacaoSenha.senha(), usuario.getPassword())){
            throw new ValidacaoException("Senha atual inválida. Tente novamente.");
        }
        if (!dadosAtualizacaoSenha.novaSenha().equals(dadosAtualizacaoSenha.confirmacaoNovaSenha())){
            throw new ValidacaoException("Confirmação difenrente da nova senha.");
        }

        var senhaCriptografada = passwordEncoder.encode(dadosAtualizacaoSenha.confirmacaoNovaSenha());
        usuario.atualizarSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void validacaoTokenEmail(String codigo){
        var usuario = usuarioRepository.findByToken(codigo)
                        .orElseThrow(() -> new RuntimeException("Token inválido ou expirado."));
        usuario.validacaoTokenEmail();
        usuarioRepository.save(usuario);
    }


    @Transactional
    public void addPerfilUsuario(Long id, DadosPerfil dadosPefil){
        var usuario = pesquisaPorId(id);
        var perfil = perfilRepository.findByPerfilNome(dadosPefil.perfilnome());
        usuario.addPerfil(perfil);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void inativarCadastro(Long id){
        var usuario = pesquisaPorId(id);
        usuario.inativarCadastro();
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void ativarCadastro(Long id){
        var usuario = pesquisaPorId(id);
        usuario.ativarCadastro();
        usuarioRepository.save(usuario);
    }







}
