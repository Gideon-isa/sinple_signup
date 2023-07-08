package com.gideon.jwt_1.services;

import com.gideon.jwt_1.dtos.SignupRequest;
import com.gideon.jwt_1.dtos.UserDTO;
import com.gideon.jwt_1.models.User;
import com.gideon.jwt_1.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        // populate the user entity with from the signupRequest body
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // save the user
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setPhone(createdUser.getPhone());
        userDTO.setId(createdUser.getId());

        return userDTO;
    }

    @Override
    public List<UserDTO> loadAllUsers() {
        //List<SecurityUser> securityUsers = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();

        List<User> users = userRepository.findAll();

        for (User user: users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setName(user.getName());

            userDTOs.add(userDTO);
        }
        return userDTOs;

    }
}
