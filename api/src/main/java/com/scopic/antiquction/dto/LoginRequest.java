package com.scopic.antiquction.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty("username")
    protected String username;
  
    @NotBlank(message = "Password cannot be blank")
    @JsonProperty("password")
    protected String password;
}
