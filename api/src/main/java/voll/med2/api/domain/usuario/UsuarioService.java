package voll.med2.api.domain.usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.perfil.DadosPefil;
import voll.med2.api.domain.perfil.Perfil;
import voll.med2.api.domain.perfil.PerfilRepository;
import voll.med2.api.domain.perfil.Perfilnome;
import voll.med2.api.infra.email.EmailService;
import voll.med2.api.infra.seguranca.HierarquiaService;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final EmailService emailService;
    private final HierarquiaService hierarquiaService;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, EmailService emailService, HierarquiaService hierarquiaService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.emailService = emailService;
        this.hierarquiaService = hierarquiaService;
    }

    @Transactional
    public Usuario cadastroUsuario(DadosCadastroUsuario dadosCadastroUsuario){
        var senhaCriptografada = passwordEncoder.encode(dadosCadastroUsuario.senha());
        var perfil = perfilRepository.findByNome(Perfilnome.PACIENTE);
        var usuario = new Usuario(dadosCadastroUsuario, senhaCriptografada, perfil);
        usuarioRepository.save(usuario);
        emailService.enviarEmailVerificacao(usuario);
        return usuario;
    }

    @Transactional
    public Page<DadosListagemUsuarios> listarUsuariosCadastrados(Pageable pageable){
        return usuarioRepository.findByAtivoTrue(pageable).map(DadosListagemUsuarios::new);
    }

    @Transactional
    public Usuario pesquisaPorNome(String nomeCompleto){
        return usuarioRepository.findByNomeCompleto(nomeCompleto)
                .orElseThrow(() -> new RuntimeException("Nome não encontrado."));
    }

    @Transactional
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
    }


    @Transactional
    public Usuario addPerfilUsuario(Usuario logado, Long id, DadosPefil dadosPefil){
        var usuario = pesquisaPorId(id);
        if (hierarquiaService.usuarioNaoTemPermissaoAddPerfil(logado, "ROLE_ADMINISTRADOR")){
            throw new ValidacaoException("Usuário sem permissão para efetuar este comando.");
        }
        var perfil = perfilRepository.findByNome(dadosPefil.perfilnome());
        usuario.addPerfil(perfil);
        return usuario;
    }

    @Transactional
    public void inativarCadastro(Usuario logado, Long id){
        var usuario = pesquisaPorId(id);
        if (hierarquiaService.usuarioNaoTemPermissaoAddPerfil(logado, "ROLE_ADMINISTRADOR")){
            throw new ValidacaoException("Usuário sem permissão para efetuar este comando.");
        }
        usuario.inativarCadastro();
    }

    @Transactional
    public void ativarCadastro(Usuario logado, Long id){
        var usuario = pesquisaPorId(id);
        if (hierarquiaService.usuarioNaoTemPermissaoAddPerfil(logado, "ROLE_ADMINISTRADOR")){
            throw new ValidacaoException("Usuário sem permissão para efetuar este comando.");
        }
        usuario.ativarCadastro();
    }







}
