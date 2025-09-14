package voll.med2.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository02 extends JpaRepository<Usuario02, Long> {
    Optional<Usuario02> findByLogin(String username);
}
