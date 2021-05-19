package com.scopic.antiquction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.scopic.antiquction.model.User;
import com.scopic.antiquction.model.UserType;
import com.scopic.antiquction.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

  private UserRepository repository;

  @Autowired
  public AccountDetailsService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    Optional<User> entity = repository.findUserByUsername(username);
    if (!entity.isPresent()) {
      // TODO: fix throwing exception
      //throw new AccountNotFoundException("Account with the given username not found");
    }

    User user = entity.get();

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + (user.getUserType() == UserType.ADMIN ? "ADMIN" : "REGULAR")));

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
  }
}