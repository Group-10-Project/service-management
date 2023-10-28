package com.review.servicemanagement.exceptions;

import com.review.servicemanagement.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException ex) {
        ExceptionDTO exception = new ExceptionDTO(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity(
                exception
                ,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateValueException.class)
    private ResponseEntity<ExceptionDTO>  handleDuplicateValueException(DuplicateValueException ex) {
        ExceptionDTO exception = new ExceptionDTO(HttpStatus.FOUND, ex.getMessage());

        return new ResponseEntity(
                exception
                ,
                HttpStatus.FOUND);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionDTO>  ExceptionHandling(Exception ex) {
        ExceptionDTO exception = new ExceptionDTO(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());

        return new ResponseEntity(
                exception
                ,
                HttpStatus.NOT_ACCEPTABLE);
    }
}
