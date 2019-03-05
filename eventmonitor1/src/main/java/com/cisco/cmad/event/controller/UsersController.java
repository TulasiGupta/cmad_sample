package com.cisco.cmad.event.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.cmad.event.mongo.services.UserService;
import com.cisco.cmad.event.response.EventResponse;

/**
 * @author tcheedel https://dzone.com/articles/cors-support-spring-framework
 */
@CrossOrigin
@RestController
public class UsersController {

    @Autowired
    UserService usersService;

    @RequestMapping(value = "/user", method = {
            RequestMethod.POST })

    public ResponseEntity<EventResponse> createUser(@RequestBody Map<String, Object> body) {
        String userName = (String) body.get("userName");
        String password = (String) body.get("password");
        System.out.println(userName);
        System.out.println(password);
        usersService.createUser(userName, password);

        EventResponse er = new EventResponse();
        er.setStatus("User created");

        return ResponseEntity.status(HttpStatus.OK).body(er);

    }

    @RequestMapping(value = "/login", method = {
            RequestMethod.POST })

    public ResponseEntity<EventResponse> loginUser(@RequestBody Map<String, Object> body) {
        String userName = (String) body.get("userName");
        String password = (String) body.get("password");
        System.out.println("in loginuser....");
        EventResponse er = new EventResponse();
        if (usersService.findUser(userName, password)) {
            er.setStatus("True");
        } else {
            er.setStatus("False");
        }
        return ResponseEntity.status(HttpStatus.OK).body(er);

    }

    @RequestMapping(value = "/deleteuser")
    public ResponseEntity<String> removeUser(@RequestParam("username") String userName) {

        usersService.removeUser(userName);
        return ResponseEntity.status(HttpStatus.OK).body("user deleted");

    }

}

// https://stackoverflow.com/questions/49670209/can-spring-map-post-parameters-by-a-way-other-than-requestbody