package voll.med2.api.infra.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
@RestControllerAdvice
public class TratadorDeErros {

    private record DadosErroValidacao(String campo, String mensagem){
        private DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ErroResponse(int status, String erro, String mensagem, LocalDateTime timeStamp){

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> tratarErro404(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(404,"Nof Found",exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErroResponse> tratarRotaNaoEncontradao404(NoHandlerFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(404,"Not Found","Rota inexistente"+exception.getRequestURL()
                , LocalDateTime.now()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException exception){
        var erro = exception.getFieldErrors().stream().map(DadosErroValidacao::new).toList();
        return ResponseEntity.ok(erro);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErroResponse> tratarErroJsonInvalido(HttpMessageConversionException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(400,"Bad Request","Erro Json não formatado ou campo inválido."
                ,LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarErroParamentro(MethodArgumentTypeMismatchException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(400,"Bad Request"
                        ,"Erro de paramentro inválido"+exception.getMessage()
                ,LocalDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarErro409(DataIntegrityViolationException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(409,"Conflict","Erro violação de dados"
                ,LocalDateTime.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> tratarErro403(AccessDeniedException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErroResponse(403,"Forbidden","Acesso negado"
                ,LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErro500(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponse(500,"Internal Server Error",
                        "Erro inesperado - "+exception.getMessage()
                ,LocalDateTime.now()));
    }

}
