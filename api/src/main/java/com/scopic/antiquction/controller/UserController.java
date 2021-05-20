package com.scopic.antiquction.controller;

import javax.validation.Valid;

import com.scopic.antiquction.dto.LoginRequest;
import com.scopic.antiquction.dto.LoginResponse;
import com.scopic.antiquction.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return userService.login(request);
    }
}
