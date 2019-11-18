package com.penniless.springboot.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class LoginUserRequest {
  private String email;
  private String password;
}
