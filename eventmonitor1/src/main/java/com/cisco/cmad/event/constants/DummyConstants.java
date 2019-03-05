/**
 * 
 */
package com.cisco.cmad.event.constants;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author tcheedel
 *
 */
public class DummyConstants {
    
    private List<String> messages = new ArrayList<String>(10);
    
    public DummyConstants() {
        this.populateMessages();
    }
    
    public void populateMessages() {        
        messages.add("Too many connections open to DB:WARNING");
        messages.add("Interface gig0/0 is down:WARNING");
        messages.add("User James logged in successfully:INFO");
        messages.add("High availability connection to device is down:WARNING");
        messages.add("Invalid credentials:ERROR");
        messages.add("unable to connect to database:ERROR");
        messages.add("unable to receive connection events:ERROR");
        messages.add("Too many connections open to DB:WARNING");
        messages.add("new module added successfully:INFO");
        messages.add("Interface gig0/0 is up:INFO");        
    }
    
    public List<String> getMessages() {
        return this.messages;
    }

}
