package com.penniless.springboot.controller.v1;

import com.penniless.springboot.model.dto.DeleteUserDto;
import com.penniless.springboot.model.dto.LoginDto;
import com.penniless.springboot.model.dto.UserDto;
import com.penniless.springboot.model.dto.UpdateUserDto;
import com.penniless.springboot.model.request.DeleteUserRequest;
import com.penniless.springboot.model.request.LoginUserRequest;
import com.penniless.springboot.model.request.RegisterUserRequest;
import com.penniless.springboot.model.request.UpdateUserRequest;
import com.penniless.springboot.model.response.AuthUserResponse;
import com.penniless.springboot.service.UserService;
import com.penniless.springboot.util.UserMapper;
import com.penniless.springboot.util.Util;
import com.penniless.springboot.validation.ValidExternalId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public ResponseEntity getAllUsers() {
    return ResponseEntity.ok().body(userService.getAllUsers()
        .stream()
        .map(UserMapper::toUserDto)
        .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity getUser(@PathVariable("id") @ValidExternalId String externalId) {
    return ResponseEntity.ok().body(userService.getUserById(externalId));
  }

  @GetMapping("load/{email}")
  public ResponseEntity getUserByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(userService.getUserByEmail(email));
  }

  @PostMapping("/register")
  public ResponseEntity registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
    log.info("Register user with info: {}", registerUserRequest);
    String hashedPassword = Util.saltAndHashPw(registerUserRequest.getPassword());
    UserDto userDto = new UserDto()
        .setEmail(registerUserRequest.getEmail())
        .setFirstName(registerUserRequest.getFirstName())
        .setLastName(registerUserRequest.getLastName())
        .setPassword(hashedPassword);

    UserDto newUser = userService.registerNewUser(userDto);
    String jwt = Util.createToken(newUser.getEmail(), newUser.getExternalId());

    // TODO:: authenticate and login after register
    AuthUserResponse res = new AuthUserResponse().setUser(newUser).setAccessToken(jwt);
    return ResponseEntity.ok().body(res);
  }

  @PostMapping("/login")
  public ResponseEntity loginUser(@RequestBody LoginUserRequest loginUserRequest) {
    LoginDto login = new LoginDto()
        .setEmail(loginUserRequest.getEmail())
        .setPassword(loginUserRequest.getPassword());

    // Controller advice should handle unauthorized as 401 instead of default 500 here.
    // No duplicate login handling.
    UserDto loggedInUser = userService.login(login);
    String jwt = Util.createToken(loggedInUser.getEmail(), loggedInUser.getExternalId());

    AuthUserResponse res = new AuthUserResponse()
        .setAccessToken(jwt)
        .setUser(loggedInUser);
    return ResponseEntity.ok().body(res);
  }

  @PostMapping("/update")
  public ResponseEntity updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
    UpdateUserDto updateUserDto = new UpdateUserDto()
        .setExternalId(updateUserRequest.getExternalId())
        .setEmail(updateUserRequest.getEmail())
        .setFirstName(updateUserRequest.getFirstName())
        .setLastName(updateUserRequest.getLastName());

    return ResponseEntity.ok().body(userService.updateUser(updateUserDto));
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteUser(@RequestBody @Valid DeleteUserRequest deleteUserRequest) {
    DeleteUserDto deleteUserDto = new DeleteUserDto()
        .setExternalId(deleteUserRequest.getExternalId())
        .setEmail(deleteUserRequest.getEmail());

    return ResponseEntity.ok().body(userService.deleteUser(deleteUserDto));
  }
}
