package com.penniless.springboot.model.request;

import lombok.Getter;

@Getter
public class DeleteUserRequest {
  private String externalId;
  private String email;
  private String password;
}
