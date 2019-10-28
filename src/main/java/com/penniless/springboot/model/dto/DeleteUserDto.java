package com.penniless.springboot.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class DeleteUserDto {
  private String externalId;
  private String email;
}
