package com.penniless.springboot.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RegisterUserRequest {
  private String email;
  private String firstName;
  private String lastName;
  private String password;
}
