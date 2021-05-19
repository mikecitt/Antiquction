package com.scopic.antiquction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class User extends Model {
    @NotBlank
    @Size(min = 5, max = 15)
    @Column(nullable = false)
    private String username;

    @NotBlank
    private String password;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
