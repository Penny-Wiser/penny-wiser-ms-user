package com.penniless.springboot.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CreateUserRequest {
    private final String name;
    private final String password;
}
