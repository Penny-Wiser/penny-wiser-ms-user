package com.penniless.springboot.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdateUserRequest {
  private String externalId;
  private String email;
  private String firstName;
  private String lastName;
}
