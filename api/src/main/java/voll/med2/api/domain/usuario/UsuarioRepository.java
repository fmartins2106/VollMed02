package voll.med2.api.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login); // Retorna Optional

    Optional<Usuario> findByEmailIgnoreCaseAndVerificadoTrue(String subject);

    Optional<Usuario> FindByEmail(String usuario);

    Page<Usuario> findByAtivoTrue(Pageable pageable);

    Optional<Usuario> findByNomeCompleto(String nomeCompleto);

    Optional<Usuario> findByToken(String codigo);
}
