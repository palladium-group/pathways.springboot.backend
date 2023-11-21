package com.pathways.controllers;

import com.pathways.models.ApplicationUser;
import com.pathways.payload.request.RegisterRequest;
import com.pathways.payload.response.LoggedInUserResponseDTO;
import com.pathways.repository.UserRepository;
import com.pathways.services.KeyCloakAdminClient;
import com.pathways.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private KeyCloakAdminClient keyCloakAdminClient;
    private UserService userService;
    private UserRepository userRepository;

    public UserController(KeyCloakAdminClient keyCloakAdminClient, UserService userService, UserRepository userRepository) {
        this.keyCloakAdminClient = keyCloakAdminClient;
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
    public ResponseEntity<?> getAllUsers() {
        try {
            var users = keyCloakAdminClient.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/register")
    public boolean registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return keyCloakAdminClient.CreateUser(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(), true);
    }

    @GetMapping("/get/{userId}")
    public Optional<ApplicationUser> getUserById(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    @GetMapping("/user-permissions/{sub}")
    public List<String> getUserPermissions(@PathVariable String sub) {
        if (sub != null) {
            var userId = UUID.fromString(sub);
            return userService.getUserPermissions(userId);
        } else {
            return Collections.<String>emptyList();
        }
    }
}
