package com.cisco.cmad.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.cisco.cmad.event.mongo.services.UserService;


/**
 * @author tcheedel
 *
 */
@PropertySource("classpath:application.properties") 
@SpringBootApplication
@EnableAutoConfiguration
public class EventsApplication implements CommandLineRunner {

    @Autowired
    UserService userService;
    
	public static void main(String[] args) {		
		SpringApplication.run(EventsApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("in command line runner");
        userService.createAdminUser();
        System.out.println("IPAddresses in database are "+ userService.fetchIPAddresses());
        userService.checkAndUpdateIPAddresses();
        System.out.println("IPAddresses in database after update "+ userService.fetchIPAddresses());
        System.out.println("Done creating user");
    }
}
