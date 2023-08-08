package com.pathways.controllers;

import com.pathways.models.ApplicationUser;
import com.pathways.payload.request.LoginRequest;
import com.pathways.payload.response.LoginResponseDTO;
import com.pathways.payload.request.RegisterRequest;
import com.pathways.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return authenticationService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequest loginRequest) {
        var user = authenticationService.loginUser(loginRequest.username(), loginRequest.password());
        if (user.getUser() == null) {
            throw new AccessDeniedException("Unauthorized to access this resource");
        }
        return user;
    }
}
