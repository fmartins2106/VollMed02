package voll.med2.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> tratarErro404(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse("404","Not Found",exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> trataErro400(MethodArgumentNotValidException exception){
        var erro = exception.getFieldErrors().stream().map(DadosErroValidacao::new);
        return ResponseEntity.ok(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarErroJsonInvalido(HttpMessageNotReadableException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse("400","Bad Request",
                        "Json não formatado ou campo inválido.", LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarErroParamentroInvalido(MethodArgumentTypeMismatchException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse("400","Bad Request",
                        "Parametro inválido."+exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarErro409(DataIntegrityViolationException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse("409","Conflict",
                        "Erro de violação de dados.", LocalDateTime.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> tratarErro403(AccessDeniedException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErroResponse("403","Forbidden","Acesso negado.",LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErro500(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponse("500","Internal Server Error",
                        "Erro inesperado"+exception.getMessage(),LocalDateTime.now()));
    }

    private record DadosErroValidacao(String campo, String mensagem){
        private DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ErroResponse(String status, String  erro, String mensagem, LocalDateTime timeStamp){
    }


}
