package mx.edu.utez.PrestamosyLibros.exception;


import mx.edu.utez.PrestamosyLibros.exception.customExceptions.BadRequestException;
import mx.edu.utez.PrestamosyLibros.exception.customExceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarBodyInvalido(HttpMessageNotReadableException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El cuerpo de la peticion es invalido, revisa el JSON enviado");
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> manejarBadRequest(BadRequestException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNotFound(NotFoundException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errores);
    }
}
