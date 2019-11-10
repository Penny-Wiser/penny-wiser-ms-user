package com.penniless.springboot.model.request;

import com.penniless.springboot.validation.ValidExternalId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter @NoArgsConstructor
public class UpdateUserRequest {
  private @ValidExternalId String externalId;
  private @Email @NotEmpty String email;
  private @NotEmpty String firstName;
  private String lastName;
}
