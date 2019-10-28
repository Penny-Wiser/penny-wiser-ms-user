package com.penniless.springboot.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateUserDto {
  private String externalId;
  private String email;
  private String firstName;
  private String lastName;
}
