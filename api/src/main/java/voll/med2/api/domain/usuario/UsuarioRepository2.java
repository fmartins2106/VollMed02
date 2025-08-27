package voll.med2.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository2 extends JpaRepository<Usuario02, Long> {


    UserDetails findByLogin(String login);
}
