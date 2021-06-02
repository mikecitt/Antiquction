package com.scopic.antiquction.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import com.scopic.antiquction.dto.AutobidSettingsDTO;
import com.scopic.antiquction.dto.LoginRequest;
import com.scopic.antiquction.dto.LoginResponse;
import com.scopic.antiquction.dto.WhoDTO;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@Api(value = "ItemController", description = "Operations for authentication and user settings.")
public class UserController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "Log in as user or admin")
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @ApiOperation(value = "Register new user")
    @PostMapping("/auth/register")
    public ResponseEntity<Void> register(@RequestBody @Valid LoginRequest request) {
        Boolean success = userService.register(request);
        if(success)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Get logged user details by passed token")
    @GetMapping("/auth/whoami")
    public WhoDTO user(Principal user) {
        Optional<User> optUser = this.userService.findUser(user.getName());
        if(!optUser.isPresent())
            return null;    
        return new WhoDTO(optUser.get());
    }

    @ApiOperation(value = "Save new user settings for bidding")
    @PostMapping("/settings")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<Void> setupAutobid(@RequestBody AutobidSettingsDTO settings, Principal user) {
        Optional<User> u = this.userService.findUser(user.getName());
        if(!u.isPresent())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        this.userService.setupAutoBid(settings, u.get().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "View user settings for bidding")
    @GetMapping("/settings")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<AutobidSettingsDTO> getAutobidSettings(Principal user) {
        Optional<User> u = this.userService.findUser(user.getName());
        if(!u.isPresent())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        AutobidSettingsDTO settings = new AutobidSettingsDTO();
        settings.setMaxAutoBid(u.get().getMaxAutoBid());
        settings.setNotificationAutoBid(u.get().getNotificationAutoBid());
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }
}
