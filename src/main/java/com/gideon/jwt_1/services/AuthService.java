package com.gideon.jwt_1.services;

import com.gideon.jwt_1.dtos.SignupRequest;
import com.gideon.jwt_1.dtos.UserDTO;
import com.gideon.jwt_1.security.SecurityUser;

import java.util.List;

public interface AuthService {
    UserDTO createUser(SignupRequest signupRequest);
    List<UserDTO> loadAllUsers();
}
