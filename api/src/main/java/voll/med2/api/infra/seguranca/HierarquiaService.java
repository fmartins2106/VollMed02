package voll.med2.api.infra.seguranca;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.usuario.Usuario;

import java.util.List;

@Service
public class HierarquiaService {


    private final RoleHierarchy roleHierarchy;

    public HierarquiaService(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }


    public boolean usuarioNaoTemPermissaoAddPerfil(Usuario logado, String perfilDesejado){
        return  logado.getAuthorities().stream()
                .flatMap(autoridade ->
                        roleHierarchy.getReachableGrantedAuthorities(List.of(autoridade))
                                .stream()).noneMatch(perfil ->
                        perfil.getAuthority().equals(perfilDesejado));
    }



}
