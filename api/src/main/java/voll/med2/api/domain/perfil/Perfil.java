package voll.med2.api.domain.perfil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "Perfil")
@Table(name = "perfis")
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "perfilNome") // igual ao nome no banco
    private Perfilnome perfilNome;

    @Override
    public String getAuthority() {
        return "ROLE_"+perfilNome;
    }
}
