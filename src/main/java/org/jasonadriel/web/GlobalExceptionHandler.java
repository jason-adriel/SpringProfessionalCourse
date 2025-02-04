package org.jasonadriel.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.jasonadriel.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .toList();
        String errorMessage = ex.getMessage();
        return new ErrorDto(fieldErrors, errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleConstraintViolationException(final ConstraintViolationException ex) {
        List<String> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath().toString())
                .toList();
        String errorMessage = ex.getMessage();
        return new ErrorDto(fieldErrors, errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        String errorMessage = ex.getMessage();
        Throwable cause = ex.getCause();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(errorMessage);
        if (cause instanceof JsonMappingException jme) {
            errorDto.setInvalidFields(jme.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .toList()
            );
        } else {
            errorDto.setInvalidFields(List.of("Invalid request body."));
        }
        return errorDto;
    }
}
