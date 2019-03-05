/**
 * 
 */
package com.cisco.cmad.event.response;

/**
 * @author tcheedel
 *
 */
public class EventResponse {
    
    private String status;
    private String errMessage;
    private boolean isError;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

}
