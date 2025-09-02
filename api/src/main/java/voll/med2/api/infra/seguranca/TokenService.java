package voll.med2.api.infra.seguranca; // Define o pacote onde a classe está localizada

import com.auth0.jwt.JWT; // Importa a classe para criar e manipular tokens JWT
import com.auth0.jwt.algorithms.Algorithm; // Importa os algoritmos de criptografia para assinar/verificar o token
import com.auth0.jwt.exceptions.JWTCreationException; // Exceção lançada se ocorrer erro ao criar o token
import com.auth0.jwt.exceptions.JWTVerificationException; // Exceção lançada se ocorrer erro ao verificar o token
import org.springframework.beans.factory.annotation.Value; // Permite injetar valores do application.properties no código
import org.springframework.stereotype.Service; // Marca a classe como um serviço do Spring
import voll.med2.api.domain.usuario.Usuario; // Importa a entidade/objeto de usuário

import java.time.Instant; // Representa um ponto fixo na linha do tempo (UTC)
import java.time.ZoneId; // Representa um fuso horário
import java.time.ZonedDateTime; // Representa data e hora com fuso horário

@Service // Indica que essa classe é um componente de serviço gerenciado pelo Spring
public class TokenService {

    @Value("${api.security.token.secret}") // Injeta no campo 'secret' o valor definido no application.properties
    private String secret; // Chave secreta usada para assinar/verificar o token

    public String gerarTokenJWT(Usuario usuario){ // Método privado que gera o token JWT para um usuário
        System.out.println(secret); // Imprime no console a chave secreta (geralmente usado só para debug)
        try {
            var algoritimo = Algorithm.HMAC256(secret); // Cria o algoritmo HMAC com a chave secreta
            return JWT.create() // Inicia a construção do token
                    .withIssuer("API Med_voll.") // Define o emissor do token (quem está gerando)
                    .withSubject(usuario.getLogin()) // Define o "dono" do token (usuário autenticado)
                    .withExpiresAt(dataExpiracao()) // Define a data de expiração do token
                    .sign(algoritimo); // Assina o token com o algoritmo e a chave secreta
        }catch (JWTCreationException exception){ // Captura erro de criação do token
            throw new RuntimeException("Erro ao gerar o token.",exception); // Lança exceção customizada
        }
    }

    public Instant dataExpiracao(){ // Método que calcula a data de expiração do token
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")) // Pega a hora atual no fuso horário de SP
                .plusHours(6) // Adiciona 6 horas de validade
                .toInstant(); // Converte para Instant (UTC)
    }

    public String getSuject(String tokenJWT){ // Método para validar e extrair o "subject" (usuário) de um token
        try {
            var algoritimo = Algorithm.HMAC256(secret); // Cria novamente o algoritmo HMAC com a chave secreta
            return JWT.require(algoritimo) // Configura a verificação com o algoritmo
                    .withIssuer("API Med_voll.") // Verifica se o emissor é o esperado
                    .build() // Constrói o verificador
                    .verify(tokenJWT) // Verifica a assinatura e validade do token
                    .getSubject(); // Retorna o subject (login do usuário dentro do token)
        }catch (JWTVerificationException exception){ // Captura erro de verificação (token inválido/expirado)
            throw new RuntimeException("Erro de validação de token ou token expirado.",exception); // Lança exceção customizada
        }
    }








}
