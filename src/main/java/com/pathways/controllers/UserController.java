package com.pathways.controllers;

import com.pathways.models.ApplicationUser;
import com.pathways.payload.response.LoggedInUserResponseDTO;
import com.pathways.repository.UserRepository;
import com.pathways.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public Optional<LoggedInUserResponseDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return Optional.of(new LoggedInUserResponseDTO(userRepository.findByUsername(currentUserName)));
        } else {
            return null;
        }
    }

    @GetMapping("/all")
    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get/{userId}")
    public Optional<ApplicationUser> getUserById(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    @GetMapping("/user-permissions")
    public List<String> getUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userService.getUserPermissions(currentUserName);
        } else {
            return null;
        }
    }
}
