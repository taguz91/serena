package com.taguz91.api_serena.api.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ InvalidRequestException.class })
    public ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;

        List<FieldErrorResource> errorResources = ire.getErrors().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(),
                        fieldError.getCode(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResource error = new ErrorResource(errorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<Object> handleInvalidAuthentication(InvalidAuthenticationException e, WebRequest request) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new HashMap<String, Object>() {
            {
                put("message", e.getMessage());
            }
        });
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleExceptionBase(ResourceNotFoundException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, Object>() {
            {
                put("message", e.getMessage());
            }
        });
    }

    @ExceptionHandler(NoAuthorizationException.class)
    public ResponseEntity<Object> handleNotAuthorizationException(NoAuthorizationException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<String, Object>() {
            {
                put("message", e.getMessage());
            }
        });
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<FieldErrorResource> errorResources = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorResource(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getDefaultMessage())
                )
                .collect(Collectors.toList());

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new ErrorResource(errorResources));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "No se enviaron los datos.");
        System.out.println("ERROR NO DATOS ENVIADOS: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResource handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<FieldErrorResource> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource(violation.getRootBeanClass().getName(),
                    getParam(violation.getPropertyPath().toString()),
                    violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
                    violation.getMessage());
            errors.add(fieldErrorResource);
        }

        return new ErrorResource(errors);
    }

    private String getParam(String s) {
        String[] splits = s.split("\\.");
        if (splits.length == 1) {
            return s;
        } else {
            return String.join(".", Arrays.copyOfRange(splits, 2, splits.length));
        }
    }
}
