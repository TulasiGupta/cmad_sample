/**
 * 
 */
package com.cisco.cmad.event.mongo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author tcheedel
 *
 */
@Document(collection = "users")
public class Users {

    
    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
