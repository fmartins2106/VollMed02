package voll.med2.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login); // Retorna Optional

    Optional<Usuario> findByEmailIgnoreCaseAndVerificadoTrue(String subject);
}
