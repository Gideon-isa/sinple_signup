package com.gideon.jwt_1.controller;

import com.gideon.jwt_1.dtos.SignupRequest;
import com.gideon.jwt_1.dtos.UserDTO;
import com.gideon.jwt_1.models.User;
import com.gideon.jwt_1.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SignupUserController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) {
        UserDTO createUser = authService.createUser(signupRequest);

        if (createUser == null) {
            return  new ResponseEntity<>("User is not created, try again", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity <List<UserDTO>> getAllUser() {

      return new ResponseEntity<List<UserDTO>>(authService.loadAllUsers(), HttpStatus.OK);

    }
}
