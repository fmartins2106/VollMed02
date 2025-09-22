package voll.med2.api.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idusuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;

    @NotNull
    @Column(unique = true)
    private String login;
    @NotNull
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "role")
    private List<String> roles = List.of("ROLE_USER"); // role padrão

    // Controle de conta
    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false)
    private boolean bloqueado = false;

    private LocalDateTime expiracaoConta;
    private LocalDateTime expiracaoCredenciais;

    public Usuario(DadosCadastroUsuario dadosCadastroUsuario, PasswordEncoder passwordEncoder) {
        this.login = dadosCadastroUsuario.login();
        this.senha = passwordEncoder.encode(dadosCadastroUsuario.senha()); // hash da senha
    }

    private static final int MESES_EXPIRACAO_SENHA = 3;
    private static final int ANOS_EXPIRACAO_CONTA = 1;

    @PrePersist
    public void prePersist() {
        this.ativo = true;
        this.bloqueado = false;

        if (this.expiracaoConta == null) {
            this.expiracaoConta = LocalDateTime.now().plusYears(ANOS_EXPIRACAO_CONTA);
        }
        if (this.expiracaoCredenciais == null) {
            this.expiracaoCredenciais = LocalDateTime.now().plusMonths(MESES_EXPIRACAO_SENHA);
        }
    }

    private static final List<String> ROLES_VALIDAS = List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MASTER");
    /**
     * Atualiza a lista de roles do usuário.
     * Substitui as roles antigas pelas novas fornecidas.
     * Se a lista estiver vazia ou null, mantém a role padrão.
     */
    public void atualizarRoles(List<String> novasRoles) {
        if (novasRoles == null || novasRoles.isEmpty()) {
            this.roles = List.of("ROLE_USER");
        } else {
            this.roles = novasRoles.stream()
                    .filter(ROLES_VALIDAS::contains)
                    .distinct()
                    .toList();
            if (this.roles.isEmpty()) {
                this.roles = List.of("ROLE_USER");
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Retorna as permissões do usuário
        return roles.stream().map(SimpleGrantedAuthority::new).toList();    // Define que o usuário tem papel "ROLE_USER"
    }

    @Override
    public String getPassword() { // Retorna a senha do usuário
        return senha;
    }

    @Override
    public String getUsername() { // Retorna o login do usuário
        return login;
    }

    @Override
    public boolean isAccountNonExpired() { // Verifica se a conta não expirou
        if (expiracaoConta == null) return true;
        return LocalDateTime.now().isBefore(expiracaoConta);
    }

    @Override
    public boolean isAccountNonLocked() { // Verifica se a conta não está bloqueada
        return !bloqueado;
    }

    @Override
    public boolean isCredentialsNonExpired() { // Verifica se as credenciais (senha) não expiraram
        if (expiracaoCredenciais == null) return true;
        return LocalDateTime.now().isBefore(expiracaoCredenciais);
    }

    @Override
    public boolean isEnabled() { // Verifica se o usuário está habilitado
        return ativo;             // Sempre ativo
    }

    public void atualizarDadosUsuario(DadosAtualizacaoUsuario dados) {
        Optional.ofNullable(dados.bloqueado()).ifPresent(b -> this.bloqueado = b);
        Optional.ofNullable(dados.expiracaoConta()).ifPresent(e -> this.expiracaoConta = e);
    }

    public void atualizarSenha(String novaSenha, PasswordEncoder encoder) {
        this.senha = encoder.encode(novaSenha);
        this.expiracaoCredenciais = LocalDateTime.now().plusMonths(3);
    }

    public void atualizarEmail(String novoEmail, String novaSenha, PasswordEncoder encoder) {
        this.login = novoEmail;
        atualizarSenha(novaSenha, encoder); // força senha nova sempre que trocar e-mail
    }

    public void desbloquearUsuario() {
        this.bloqueado = false;
    }

    public void desbloquearUsuarioComRenovacao() {
        this.bloqueado = false;

        if (this.expiracaoConta == null || this.expiracaoConta.isBefore(LocalDateTime.now())) {
            this.expiracaoConta = LocalDateTime.now().plusYears(ANOS_EXPIRACAO_CONTA);
        }

        this.expiracaoCredenciais = LocalDateTime.now().plusMonths(MESES_EXPIRACAO_SENHA);
    }
}
