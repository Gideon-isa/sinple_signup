package com.gideon.jwt_1.services;

import com.gideon.jwt_1.dtos.SignupRequest;
import com.gideon.jwt_1.dtos.UserDTO;
import com.gideon.jwt_1.models.User;
import com.gideon.jwt_1.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        // populate the user entity with from the signupRequest body
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));

        // save the user
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setPhone(createdUser.getPhone());
        userDTO.setId(createdUser.getId());

        return userDTO;
    }
}
