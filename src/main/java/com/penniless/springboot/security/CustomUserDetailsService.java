package com.penniless.springboot.security;

import com.penniless.springboot.exception.PWException;
import com.penniless.springboot.model.dto.UserDto;
import com.penniless.springboot.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private UserService userService;

  public CustomUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      UserDto user = userService.getUserByEmail(username);
      return new User(
          user.getEmail(), user.getPassword(), true, true, true, true, Collections.emptyList());
    } catch (PWException.EntityNotFoundException e) {
      throw new UsernameNotFoundException(String.format("User with email: %s could not be found.", username));
    }
  }
}
