package com.gideon.jwt_1.controller;

import com.gideon.jwt_1.dtos.AuthenticationResponse;
import com.gideon.jwt_1.dtos.AuthenticationRequest;
import com.gideon.jwt_1.models.User;
import com.gideon.jwt_1.services.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws BadCredentialsException, IOException {

        String authEmail = authenticationRequest.getEmail();
        String authPassword = authenticationRequest.getPassword();

        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(authEmail, authPassword);
        try {
            authenticationManager.authenticate(userAuth);
        }catch (BadCredentialsException bc) {
            throw new BadCredentialsException("incorrect Username or password");
        }catch (DisabledException did) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created. Register User first");
                    return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authEmail);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);

    }

   
}
