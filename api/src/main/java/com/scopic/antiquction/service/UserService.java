package com.scopic.antiquction.service;

import static com.scopic.antiquction.security.jwt.Constants.EXPIRATION_TIME;
import static com.scopic.antiquction.security.jwt.Constants.PROVIDER;
import static com.scopic.antiquction.security.jwt.Constants.SECREY_KEY;

import java.util.Date;
import java.util.Optional;

import com.scopic.antiquction.dto.LoginRequest;
import com.scopic.antiquction.dto.LoginResponse;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> findUser(String username) {
        return repository.findUserByUsername(username);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String token = Jwts.builder().setIssuer(PROVIDER).setSubject(request.getUsername()).setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECREY_KEY).compact();
    
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        LoginResponse accountLoginResponse = new LoginResponse(token, role.substring(role.indexOf("_") + 1),
            request.getUsername(), EXPIRATION_TIME);
    
        return new ResponseEntity<>(accountLoginResponse, HttpStatus.OK);
      }
}
