package com.penniless.springboot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Accessors(chain = true)
public class UserDto {
  private String externalId;
  private String email;
  private String firstName;
  private String lastName;
  private @JsonIgnore String password;
}
