package com.penniless.springboot.service;

import com.penniless.springboot.model.User;
import com.penniless.springboot.model.dto.DeleteUserDto;
import com.penniless.springboot.model.dto.LoginDto;
import com.penniless.springboot.model.dto.UserDto;
import com.penniless.springboot.model.dto.UpdateUserDto;

import java.util.List;

public interface UserService {
  List<User> getAllUsers();
  UserDto getUserById(String externalId);
  UserDto getUserByEmail(String email);
  UserDto registerNewUser(UserDto userDto);
  UserDto login(LoginDto loginDto);
  UserDto updateUser(UpdateUserDto updateUserDto);
  UserDto deleteUser(DeleteUserDto deleteUserDto);
}
