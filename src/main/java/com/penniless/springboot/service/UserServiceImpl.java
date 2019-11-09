package com.penniless.springboot.service;

import com.penniless.springboot.dao.UserRepository;
import com.penniless.springboot.exception.*;
import com.penniless.springboot.model.User;
import com.penniless.springboot.model.dto.DeleteUserDto;
import com.penniless.springboot.model.dto.UserDto;
import com.penniless.springboot.model.dto.UpdateUserDto;
import com.penniless.springboot.util.UserMapper;
import com.penniless.springboot.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.penniless.springboot.exception.EntityType.USER;
import static com.penniless.springboot.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.penniless.springboot.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.penniless.springboot.exception.ExceptionType.ENTITY_NOT_FOUND_2;

@Service
@Getter @Setter
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public UserDto getUserById(String externalId) {
    Optional<User> user = userRepository.findByExternalId(externalId);
    if (user.isPresent()) {
      return UserMapper.toUserDto(user.get());
    }

    throw exception(USER, ENTITY_NOT_FOUND, String.valueOf(externalId));
  }

  public UserDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND_2, email));

    return UserMapper.toUserDto(user);
  }

  public UserDto registerNewUser(UserDto userDto) {
    Optional<User> persistentUser = userRepository.findByEmail(userDto.getEmail());
    if (persistentUser.isPresent()) {
      throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    // generate UUID for each new user
    // this external id is a GUID (Globally Unique Identifier) and cannot be changed
    String extId = Util.genExternalId();

    User newUser = new User()
        .setExternalId(extId)
        .setEmail(userDto.getEmail())
        .setFirstName(userDto.getFirstName())
        .setPassword(userDto.getPassword());

    if (userDto.getLastName() != null) {
      newUser.setLastName(userDto.getLastName());
    }

    // Handle db errors
    return UserMapper.toUserDto(userRepository.save(newUser));
  }

  public UserDto updateUser(UpdateUserDto updateUserDto) {
    Optional<User> persistentUser = userRepository.findByExternalId(updateUserDto.getExternalId());
    if (!persistentUser.isPresent()) {
      throw exception(USER, ENTITY_NOT_FOUND, updateUserDto.getExternalId());
    }

    User user = persistentUser.get();
    updateUser(user, updateUserDto.getFirstName(), updateUserDto.getLastName(), updateUserDto.getEmail());
    return UserMapper.toUserDto(userRepository.save(user));
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
      user.setEmail(email);
    }
  }

  public UserDto deleteUser(DeleteUserDto deleteUserDto) {
    Optional<User> persistentUser = userRepository.findByExternalId(deleteUserDto.getExternalId());
    if (!persistentUser.isPresent()) {
      throw exception(USER, ENTITY_NOT_FOUND,deleteUserDto.getExternalId());
    }

    // user can only be deleted if there are no outstanding bills

    User user = persistentUser.get();
    userRepository.deleteById(user.getId());
    return UserMapper.toUserDto(user);
  }

  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
    return PWException.throwException(entityType, exceptionType, args);
  }
}
