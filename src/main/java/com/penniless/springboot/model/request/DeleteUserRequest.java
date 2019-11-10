package com.penniless.springboot.model.request;

import com.penniless.springboot.validation.ValidExternalId;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class DeleteUserRequest {
  private @ValidExternalId String externalId;
  private @Email @NotEmpty String email;
  private @NotEmpty String password;
}
