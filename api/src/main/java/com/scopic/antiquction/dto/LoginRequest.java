package com.scopic.antiquction.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty("username")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    protected String username;
  
    @NotBlank(message = "Password cannot be blank")
    @JsonProperty("password")
    protected String password;
}
