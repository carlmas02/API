package com.example.Testing_JWT.controller;

import com.example.Testing_JWT.domain.UserEntity;
import com.example.Testing_JWT.exception.UserAlreadyExistsException;
import com.example.Testing_JWT.service.ServiceTokenGeneratorImpl;
import com.example.Testing_JWT.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
  @Autowired UserServiceImpl userServiceImpl;

  @Autowired ServiceTokenGeneratorImpl securityTokenGeneratorImpl;

  @Autowired RestTemplate restTemplate;

  /* Using these two mappings users can login to the account as well as even create a new account */

  @PostMapping("/register")
  public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity)
      throws UserAlreadyExistsException {
    Map<String, String> jwttoken;

    UserEntity userEntityCreated = userServiceImpl.addUser(userEntity);

    // DONE
    String url = "http://USER-SERVICE/user-service/api/users";
    //    String url = "http://localhost:8080/api/users";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    HttpEntity<UserEntity> request = new HttpEntity<>(userEntity, headers);
    System.out.println("bfr request");
    ResponseEntity<String> response =
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("User created successfully.");
    } else {
      System.out.println("Failed to create user. Status code: " + response.getStatusCode());
    }

    jwttoken = securityTokenGeneratorImpl.generateToken(userEntityCreated);

    return new ResponseEntity<>(jwttoken.get("token"), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> checkUser(@RequestBody UserEntity userEntity) {
    Map<String, String> jwttoken;

    UserEntity userEntity1 = userServiceImpl.checkEmailAndPassword(userEntity);
    if (userEntity1 != null) {
      jwttoken = securityTokenGeneratorImpl.generateToken(userEntity);
      return new ResponseEntity<>(jwttoken, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Check Credentials", HttpStatus.UNAUTHORIZED);
    }
  }
}
