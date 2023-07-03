package com.gideon.jwt_1.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;

}
