package com.scopic.antiquction.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
    private String username;
    private Long expiresIn;

    public LoginResponse(String token, String role, String username, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.role = role;
        this.username = username;
    }
}
