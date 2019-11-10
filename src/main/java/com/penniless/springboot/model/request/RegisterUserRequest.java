package com.penniless.springboot.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter @NoArgsConstructor
public class RegisterUserRequest {
  private @Email @NotEmpty String email;
  private @NotEmpty String firstName;
  private String lastName;
  private @NotEmpty String password;
}
