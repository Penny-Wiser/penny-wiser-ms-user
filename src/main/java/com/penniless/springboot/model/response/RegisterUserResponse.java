package com.penniless.springboot.model.response;

import com.penniless.springboot.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterUserResponse {
  private UserDto user;
  private String accessToken;
}
