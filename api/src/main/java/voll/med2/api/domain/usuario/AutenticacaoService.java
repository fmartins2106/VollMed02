package voll.med2.api.domain.usuario; // Define o pacote da classe

import org.springframework.beans.factory.annotation.Autowired; // Permite injetar dependências automaticamente
import org.springframework.security.core.userdetails.UserDetails; // Interface que representa um usuário para o Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Interface que define serviço para buscar usuários
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exceção lançada se usuário não for encontrado
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Marca a classe como um Service do Spring (bean gerenciado)

@Service // Informa ao Spring que esta classe é um serviço e deve ser gerenciada como bean
public class AutenticacaoService implements UserDetailsService { // Implementa UserDetailsService para autenticação

    @Autowired // Injeta automaticamente a dependência do repository
    private UsuarioRepository usuarioRepository; // Repositório para acessar dados de usuários no banco

    /**
     * Implementação do metodo da interface UserDetailsService.
     *
     * Funcionalidade:
     * - Recebe um username (login) e busca o usuário correspondente no banco.
     * - Se o usuário existir, retorna um objeto que implementa UserDetails, contendo:
     *      • login
     *      • senha (hash)
     *      • roles/permissões
     * - Se não existir, lança UsernameNotFoundException (Spring Security trata adequadamente).
     *
     * Objetivo:
     * Permitir que o Spring Security consiga autenticar e autorizar o usuário
     * baseado nos dados carregados do banco.
     */
    @Override // Sobrescreve o metodo da interface UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo login no repositório e lança exceção se não existir
        return usuarioRepository.findByLogin(username)               // Consulta o usuário no banco
                .orElseThrow(() -> new UsernameNotFoundException(    // Se não existir, lança a exceção adequada
                        "Usuário não encontrado: " + username));
        // Retorna o usuário encontrado, que implementa UserDetails
    }






}
