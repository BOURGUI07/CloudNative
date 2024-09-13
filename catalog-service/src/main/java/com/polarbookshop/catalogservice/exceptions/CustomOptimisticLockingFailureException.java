package com.polarbookshop.catalogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomOptimisticLockingFailureException extends RuntimeException {
    public CustomOptimisticLockingFailureException(String isbn) {
        super("The Book With ISBN: " + isbn + " Has Been Updated By Another User");
    }
}
