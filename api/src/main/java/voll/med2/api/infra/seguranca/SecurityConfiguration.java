package voll.med2.api.infra.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // necessário para @PreAuthorize
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter; // Injeta o filtro de segurança personalizado que será executado antes do filtro de autenticação padrão


    /**
     * Configura a cadeia de segurança (SecurityFilterChain) do Spring Security.
     *
     * Funcionalidades principais:
     * 1. Desativa CSRF (útil para APIs REST stateless).
     * 2. Define que a aplicação não mantém sessão (SessionCreationPolicy.STATELESS).
     * 3. Configura autorização de endpoints:
     *    - "/login" e "/usuarios" liberados para todos.
     *    - Swagger e documentação da API liberados para todos.
     *    - Todos os outros endpoints exigem autenticação.
     * 4. Adiciona o filtro customizado de JWT (securityFilter) antes do filtro padrão
     *    de autenticação (UsernamePasswordAuthenticationFilter).
     * 5. Constrói e retorna a configuração final da cadeia de filtros.
     *
     * Objetivo: garantir que somente usuários com JWT válido possam acessar os endpoints protegidos,
     * mantendo a API segura e sem estado (stateless).
     */
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


    /**
     * Expõe o AuthenticationManager como um bean do Spring.
     *
     * Funcionalidade:
     * - Permite que outras classes da aplicação (ex.: serviços de login)
     *   possam injetar e usar o AuthenticationManager para autenticar usuários.
     * - Recupera o AuthenticationManager configurado automaticamente pelo Spring
     *   a partir da AuthenticationConfiguration.
     *
     * Objetivo: possibilitar autenticação manual de usuários em serviços ou endpoints
     * sem depender apenas da cadeia automática de filtros.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Expõe o AuthenticationManager para ser usado em outras partes da aplicação (ex.: login manual)
    }



    /**
     * Cria o PasswordEncoder da aplicação usando BCrypt.
     *
     * Funcionalidade:
     * - BCrypt é um algoritmo de hash seguro e recomendado para senhas.
     * - Garante que senhas nunca sejam armazenadas em texto puro no banco.
     * - Permite verificar senhas de forma segura durante o login, comparando o hash.
     *
     * Exemplo de uso:
     *   String senhaHash = passwordEncoder.encode("minhaSenha123");
     *   passwordEncoder.matches("minhaSenha123", senhaHash); // retorna true
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Bean que criptografa senhas usando BCrypt (recomendado para segurança)
    }






}
