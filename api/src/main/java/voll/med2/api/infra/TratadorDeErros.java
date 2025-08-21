package voll.med2.api.infra; // Pacote da classe (organização do projeto)

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice // Torna esta classe um interceptador global de exceções de Controllers REST
public class TratadorDeErros { // Centraliza o tratamento e padroniza as respostas de erro


    //------------------------

    @ExceptionHandler(EntityNotFoundException.class)
    // Quando uma entidade não é encontrada (ex.: buscar por ID inexistente)
    public ResponseEntity<ErroResponse> tratarErro404(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // HTTP 404
                .body(new ErroResponse(404, "Not Found", ex.getMessage(), LocalDateTime.now())); // Corpo padronizado
    }


    @ExceptionHandler(MethodArgumentNotValidException.class) // Quando falha a validação do @Valid nos DTOs
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream().map(DadosErroValidacao::new).toList(); // Mapeia erros de campo -> DTO leve
        return ResponseEntity.badRequest().body(erros); // HTTP 400 com lista de {campo, mensagem}
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    // Quando o corpo JSON está inválido ou com tipos incompatíveis
    public ResponseEntity<ErroResponse> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest() // HTTP 400
                .body(new ErroResponse(400, "Bad Request", "JSON mal formatado ou campos inválidos", LocalDateTime.now())); // Mensagem amigável
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    // Quando um parâmetro (path/query) não converte para o tipo esperado
    public ResponseEntity<ErroResponse> tratarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest() // HTTP 400
                .body(new ErroResponse(400, "Bad Request",
                        "Parâmetro inválido: " + ex.getName(), LocalDateTime.now())); // Indica qual parâmetro quebrou
    }


    @ExceptionHandler(DataIntegrityViolationException.class) // Quebra de constraint do banco (unique, FK, etc.)
    public ResponseEntity<ErroResponse> tratarErro409(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT) // HTTP 409 (conflito de estado)
                .body(new ErroResponse(409, "Conflict", "Violação de integridade de dados", LocalDateTime.now())); // Mensagem genérica segura
    }


    @ExceptionHandler(AccessDeniedException.class) // Usuário autenticado porém sem permissão para o recurso
    public ResponseEntity<ErroResponse> tratarErro403(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN) // HTTP 403
                .body(new ErroResponse(403, "Forbidden", "Acesso negado", LocalDateTime.now())); // Evita expor detalhes de segurança
    }

    @ExceptionHandler(Exception.class) // Fallback genérico para qualquer erro não tratado acima
    public ResponseEntity<ErroResponse> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // HTTP 500
                .body(new ErroResponse(500, "Internal Server Error",
                        "Erro inesperado: " + ex.getMessage(), LocalDateTime.now())); // Mensagem genérica + detalhe
    }




    private record DadosErroValidacao(String campo, String mensagem) { // DTO para retornar erros de validação por campo
        public DadosErroValidacao(FieldError erro) { // Constrói a partir do FieldError do Spring
            this(erro.getField(), erro.getDefaultMessage()); // Nome do campo + mensagem traduzida do validator
        }
    }

    private record ErroResponse(int status, String erro, String mensagem,
                                LocalDateTime timestamp) { // Modelo padrão de erro
        // status: código HTTP; erro: texto do status; mensagem: detalhe; timestamp: quando ocorreu
    }
}
