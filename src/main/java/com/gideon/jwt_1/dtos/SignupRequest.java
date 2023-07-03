package com.gideon.jwt_1.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
}
