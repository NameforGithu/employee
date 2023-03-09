package com.neusta.exception;

import com.neusta.exception.response.ErrorResponse;
import com.neusta.exception.response.ValidationResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse message = new ErrorResponse("404", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ValidationResponse> handleValidationsException(PSQLException ex){
        ValidationResponse message = new ValidationResponse("500", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
