/**
 * 
 */
package com.cisco.cmad.event.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.cisco.cmad.event.security.SecurityConstants.EXPIRATION_TIME;
import static com.cisco.cmad.event.security.SecurityConstants.HEADER_STRING;
import static com.cisco.cmad.event.security.SecurityConstants.SECRET;
import static com.cisco.cmad.event.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.cisco.cmad.event.mongo.dao.Users;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            System.out.println("in attemptAuthentication");
            Users creds = new ObjectMapper().readValue(req.getInputStream(), Users.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getName(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        System.out.println("in successfulAuthentication");
        String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        System.out.println(res.getHeader("Access-Control-Allow-Origin"));
        System.out.println(res.getHeader("Authorization"));
        System.out.println(req.getRequestURI());
    }
}