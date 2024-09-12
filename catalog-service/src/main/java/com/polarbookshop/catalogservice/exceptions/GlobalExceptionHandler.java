package com.polarbookshop.catalogservice.exceptions;

import com.polarbookshop.catalogservice.dto.ErrorResponse;
import com.polarbookshop.catalogservice.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BookNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorResponse = new ErrorResponse(request.getDescription(false),status, status.value(), ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(BookAlreadyExistsException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var errorResponse = new ErrorResponse(request.getDescription(false),status, status.value(), ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var map = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(x->map.put(((FieldError)x).getField(),x.getDefaultMessage()));
        var errorResponse = new ValidationErrorResponse(request.getDescription(false),status, status.value(), map,System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorResponse = new ErrorResponse(request.getDescription(false),status, status.value(), ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, status);
    }
}
