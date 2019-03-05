package com.cisco.cmad.event.exceptions;

import java.util.Date;

/**
 * @author tcheedel
 *
 */
public class ErrorDetails {

    public ErrorDetails(String dateFormat, String message, String details) {
        super();
        this.dateFormat = dateFormat;
        this.message = message;
        this.details = details;
    }

    private String dateFormat;
    private String message;
    private String details;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
