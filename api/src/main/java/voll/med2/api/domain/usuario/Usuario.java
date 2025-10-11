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
import voll.med2.api.domain.perfil.Perfil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nomeCompleto;

    @NotNull
    @Column(unique = true)
    private String login;
    @NotNull
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_perfis",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private List<Perfil> perfis = new ArrayList<>();

    @Column(nullable = false)
    private Boolean ativo = true;

    private Boolean verificado;
    private String token;
    private LocalDateTime expiracaoToken;



    public Usuario(DadosCadastroUsuario dadosCadastroUsuario, PasswordEncoder passwordEncoder) {
        this.login = dadosCadastroUsuario.login();
        this.senha = passwordEncoder.encode(dadosCadastroUsuario.senha()); // hash da senha
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
