package com.gideon.jwt_1.services;

import com.gideon.jwt_1.dtos.SignupRequest;
import com.gideon.jwt_1.dtos.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupRequest signupRequest);
}
