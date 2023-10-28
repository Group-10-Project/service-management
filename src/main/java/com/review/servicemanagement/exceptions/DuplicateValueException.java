package com.review.servicemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class DuplicateValueException extends  Exception{
    public DuplicateValueException(String exception){
        super(exception);
    }

}
