/**
 * 
 */
package com.cisco.cmad.event.mongo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author tcheedel
 *
 */
@Document(collection = "ipaddresses")
public class IPAddresses {
    
    private String ipaddress;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
