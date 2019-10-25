package com.penniless.springboot.controller.v1;

import com.penniless.springboot.model.User;
import com.penniless.springboot.model.request.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @GetMapping("/{id}")
  public User Get_User(@PathVariable("id") String id) {
    return User.builder().name("user with id [" + id + "]").build();
  }

  @PostMapping
  public ResponseEntity<?> createNewUser(@RequestBody CreateUserRequest createUserRequest) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
