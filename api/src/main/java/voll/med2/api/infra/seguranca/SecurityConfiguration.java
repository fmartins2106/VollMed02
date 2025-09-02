package voll.med2.api.infra.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter; // Injeta o filtro de segurança personalizado que será executado antes do filtro de autenticação padrão

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF, útil para APIs REST stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define que a aplicação não mantém sessão (API REST stateless)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll(); // Permite acesso público a /path
                    auth.requestMatchers("/usuarios").permitAll();
                    auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll(); // Permite acesso público à documentação Swagger
                    auth.anyRequest().authenticated(); // Todas as outras requisições precisam estar autenticadas
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro customizado antes do filtro padrão de autenticação
                .build(); // Constroi a configuração final da cadeia de filtros
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Expõe o AuthenticationManager para ser usado em outras partes da aplicação (ex.: login manual)
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Bean que criptografa senhas usando BCrypt (recomendado para segurança)
    }






}
