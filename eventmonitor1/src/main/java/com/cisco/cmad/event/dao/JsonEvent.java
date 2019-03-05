/**
 * 
 */
package com.cisco.cmad.event.dao;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author tcheedel
 *
 */
@Component
//@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS)
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class JsonEvent implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6265057141683354236L;
    public int id;
    private String message;
    private String type;
    private String ipaddress;
    private String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
