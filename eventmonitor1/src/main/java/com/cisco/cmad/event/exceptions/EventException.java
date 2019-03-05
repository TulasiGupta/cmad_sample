package com.cisco.cmad.event.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tcheedel
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -1826626536830816827L;

    public EventException(String exception) {
        super(exception);
    }
}
