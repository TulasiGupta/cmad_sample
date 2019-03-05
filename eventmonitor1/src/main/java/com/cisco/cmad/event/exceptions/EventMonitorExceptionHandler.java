/**
 * 
 */
package com.cisco.cmad.event.exceptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author tcheedel
 *
 */

@ControllerAdvice
@RestController
public class EventMonitorExceptionHandler extends ResponseEntityExceptionHandler {

    private DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");

    @ExceptionHandler(InvalidParameterException.class)
    public final ResponseEntity<ErrorDetails> invalidParameters(InvalidParameterException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(df.format(new Date()), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCreatedException.class)
    public final ResponseEntity<ErrorDetails> userAlreadyCreated(UserCreatedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(df.format(new Date()), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.PRECONDITION_FAILED);
    }
    
    @ExceptionHandler(AdminUserException.class)
    public final ResponseEntity<ErrorDetails> adminUserException(AdminUserException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(df.format(new Date()), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.FORBIDDEN);
    }

}
