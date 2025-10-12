package voll.med2.api.infra.seguranca;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.usuario.Usuario;

import java.util.Collection;
import java.util.List;

@Service
public class HierarquiaService {


    private final RoleHierarchy roleHierarchy;

    public HierarquiaService(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }


    // Método que verifica se o usuário logado possui uma determinada role (permissão)
    public boolean usuarioTemPermissao(Usuario logado, String roleNecessario){
        // Pega todas as roles/permissões que o usuário possui
        Collection<? extends GrantedAuthority> authorities = logado.getAuthorities();
        // Expande a lista de roles considerando a hierarquia de roles configurada no Spring Security
        // Ex: se o usuário tem ROLE_ADMIN, ele também alcança ROLE_MEDICO, ROLE_USUARIO, etc.
        return roleHierarchy.getReachableGrantedAuthorities(authorities)
                // Converte a coleção em stream e verifica se alguma role corresponde à role necessária
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + roleNecessario));
    }



//    public boolean usuarioNaoTemPermissaoAddPerfil(Usuario logado, String perfilDesejado){
//        return  logado.getAuthorities().stream()
//                .flatMap(autoridade ->
//                        roleHierarchy.getReachableGrantedAuthorities(List.of(autoridade))
//                                .stream()).noneMatch(perfil ->
//                        perfil.getAuthority().equals(perfilDesejado));
//    }



}
