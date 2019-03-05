/**
 * 
 */
package com.cisco.cmad.event.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sakahuja
 * @author tcheedel
 */
@Entity
@Table(name = "syslogevents")
public class Event {

    @Id
    private int id;
    private String message;
    private String type;
    private String ipaddress;
    private long timestamp;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", message=" + message + ", type=" + type + ", ipaddress=" + ipaddress
                + ", timestamp=" + timestamp + "]";
    }

}
