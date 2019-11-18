package com.penniless.springboot.service;

import com.penniless.springboot.dao.UserRepository;
import com.penniless.springboot.exception.*;
import com.penniless.springboot.model.User;
import com.penniless.springboot.model.dto.DeleteUserDto;
import com.penniless.springboot.model.dto.LoginDto;
import com.penniless.springboot.model.dto.UserDto;
import com.penniless.springboot.model.dto.UpdateUserDto;
import com.penniless.springboot.util.UserMapper;
import com.penniless.springboot.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.penniless.springboot.exception.EntityType.USER;
import static com.penniless.springboot.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.penniless.springboot.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.penniless.springboot.exception.ExceptionType.ENTITY_NOT_FOUND_2;
import static com.penniless.springboot.exception.ExceptionType.UNAUTHORIZED;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public UserDto getUserById(String externalId) {
    User user = userRepository.findByExternalId(externalId)
        .orElseThrow(() -> {
          log.error("Unable to find user with externalId: {}", externalId);
          throw exception(USER, ENTITY_NOT_FOUND, String.valueOf(externalId));
        });

    return UserMapper.toUserDto(user);
  }

  public UserDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email.toLowerCase())
        .orElseThrow(() -> {
          log.error("Failed to retrieve user with email: {}", email);
          throw exception(USER, ENTITY_NOT_FOUND_2, email);
        });

    return UserMapper.toUserDto(user);
  }

  public UserDto registerNewUser(UserDto userDto) {
    Optional<User> persistentUser = userRepository.findByEmail(userDto.getEmail());
    if (persistentUser.isPresent()) {
      log.error("User with email: {} already exist.", userDto.getEmail());
      throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    // generate UUID for each new user
    // this external id is a GUID (Globally Unique Identifier) and cannot be changed
    String extId = Util.genExternalId();

    User newUser = new User()
        .setExternalId(extId)
        .setEmail(userDto.getEmail().toLowerCase())
        .setFirstName(userDto.getFirstName())
        .setPassword(userDto.getPassword());

    if (userDto.getLastName() != null) {
      newUser.setLastName(userDto.getLastName());
    }

    log.info("Created new user: {}", newUser);
    return UserMapper.toUserDto(userRepository.save(newUser));
  }

  public UserDto login(LoginDto loginDto) {
    User user = userRepository.findByEmail(loginDto.getEmail().toLowerCase())
        .orElseThrow(() -> {
          log.error("Failed to retrieve user with email: {}", loginDto.getEmail());
          throw exception(USER, ENTITY_NOT_FOUND_2, loginDto.getEmail());
        });

    boolean isAuthenticated = Util.authenticatePw(loginDto.getPassword(), user.getPassword());
    if (!isAuthenticated) {
      throw exception(UNAUTHORIZED);
    }

    return UserMapper.toUserDto(user);
  }

  public UserDto updateUser(UpdateUserDto updateUserDto) {
    User user = userRepository.findByExternalId(updateUserDto.getExternalId())
        .orElseThrow(() -> {
          log.error("Failed to find user with externalId: {}", updateUserDto.getExternalId());
          throw exception(USER, ENTITY_NOT_FOUND, updateUserDto.getExternalId());
        });

    updateUser(user, updateUserDto.getFirstName(), updateUserDto.getLastName(), updateUserDto.getEmail());
    User updatedUser = userRepository.save(user);
    log.info("Updated user: {}", user);
    return UserMapper.toUserDto(updatedUser);
  }

  // is there a better way to do this?
  private void updateUser(User user, String firstName, String lastName, String email) {
    if (firstName != null) {
      user.setFirstName(firstName);
    }
    if (lastName != null) {
      user.setLastName(lastName);
    }
    if (email != null) {
      user.setEmail(email.toLowerCase());
    }
  }

  public UserDto deleteUser(DeleteUserDto deleteUserDto) {
    User user = userRepository.findByExternalId(deleteUserDto.getExternalId())
        .orElseThrow(() -> {
          log.error("Failed to find user with externalId: {}", deleteUserDto.getExternalId());
          throw exception(USER, ENTITY_NOT_FOUND,deleteUserDto.getExternalId());
        });
    // user can only be deleted if there are no outstanding bills

    userRepository.deleteById(user.getId());
    log.info("Deleted user: {}", user);
    return UserMapper.toUserDto(user);
  }

  private RuntimeException exception(ExceptionType exceptionType) {
    return PWException.throwException(exceptionType);
  }

  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
    return PWException.throwException(entityType, exceptionType, args);
  }

  public String hello() {
    return "Hello from user service!";
  }
}
