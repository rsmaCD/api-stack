package com.tw.apistack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rsma on 21/08/2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotExitException extends RuntimeException {
    public ResourceNotExitException(String message) {
        super(message);
    }
}
