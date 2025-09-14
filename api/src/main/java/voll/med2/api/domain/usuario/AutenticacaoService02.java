package voll.med2.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService02 implements UserDetailsService {

    @Autowired
    private UsuarioRepository02 usuarioRepository02;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository02.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }
}
