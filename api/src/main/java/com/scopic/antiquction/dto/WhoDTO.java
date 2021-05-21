package com.scopic.antiquction.dto;

import java.util.Collection;

import com.scopic.antiquction.model.User;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class WhoDTO {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    
    public WhoDTO(User user) {
        this.username = user.getUsername();
        this.authorities = user.getAuthorities();
    }
}
