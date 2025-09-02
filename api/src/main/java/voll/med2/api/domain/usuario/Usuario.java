package voll.med2.api.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idusuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    private String login;
    private String senha;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Retorna as permissões do usuário
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));    // Define que o usuário tem papel "ROLE_USER"
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
        return true;                       // Sempre válida
    }

    @Override
    public boolean isAccountNonLocked() { // Verifica se a conta não está bloqueada
        return true;                      // Sempre desbloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() { // Verifica se as credenciais (senha) não expiraram
        return true;                           // Sempre válidas
    }

    @Override
    public boolean isEnabled() { // Verifica se o usuário está habilitado
        return true;             // Sempre ativo
    }
}
