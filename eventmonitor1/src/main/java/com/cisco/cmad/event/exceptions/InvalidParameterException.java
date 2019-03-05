package com.cisco.cmad.event.exceptions;

/**
 * @author tcheedel
 *
 */
public class InvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = 3926048233535650939L;

    public InvalidParameterException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public InvalidParameterException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidParameterException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
