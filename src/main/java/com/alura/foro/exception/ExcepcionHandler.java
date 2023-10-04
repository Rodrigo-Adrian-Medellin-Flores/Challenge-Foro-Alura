package com.alura.foro.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExcepcionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity manejarError404(){
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> manejarError400(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<DatosErrorValidacion> datosErroresValidacion = fieldErrors.stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(datosErroresValidacion);
    }

            private record DatosErrorValidacion(String field, String message){
                public DatosErrorValidacion(FieldError fieldError){
                    this(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<DatosValidacionException> manejarErrorDeValidacion(ValidacionException e){
        DatosValidacionException datosValidacionException = new DatosValidacionException(e);
        return ResponseEntity.badRequest().body(datosValidacionException);
    }

            private record DatosValidacionException(String message) {
                public DatosValidacionException(ValidacionException e){
                    this(e.getMessage());
                }
            }
    
}
