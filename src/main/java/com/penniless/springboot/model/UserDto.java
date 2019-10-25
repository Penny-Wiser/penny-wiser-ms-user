package com.penniless.springboot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Builder
public class UserDto {
  private String name;
  private String password;
}
