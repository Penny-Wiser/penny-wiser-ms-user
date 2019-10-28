package com.penniless.springboot.util;

import com.penniless.springboot.model.User;
import com.penniless.springboot.model.dto.UserDto;

public class UserMapper {

  public static UserDto toUserDto(User user) {
    return new UserDto()
        .setExternalId(user.getExternalId())
        .setEmail(user.getEmail())
        .setFirstName(user.getFirstName())
        .setLastName(user.getLastName())
        .setPassword(user.getPassword());
  }
}
