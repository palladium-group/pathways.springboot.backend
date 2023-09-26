package com.pathways.services;

import com.pathways.models.ApplicationUser;
import com.pathways.models.Role;
import com.pathways.payload.response.LoginResponseDTO;
import com.pathways.payload.request.RegisterRequest;
import com.pathways.repository.RoleRepository;
import com.pathways.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public ApplicationUser registerUser(RegisterRequest registerRequest) throws MessagingException {
        String encodedPassword = encoder.encode(registerRequest.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        ApplicationUser user = userRepository.save(
                new ApplicationUser(
                        0,
                        registerRequest.getUsername(),
                        registerRequest.getEmail(),
                        encodedPassword,
                        registerRequest.getFirstName(),
                        registerRequest.getLastName(),
                        authorities
                )
        );

        userService.initiateConfirmUserAccount(user);
        return user;
    }

    public void updateUserRegistration(Integer userId, String firstName, String lastName) {
        Optional<ApplicationUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            ApplicationUser applicationUser = optionalUser.get();
            applicationUser.setFirstName(firstName);
            applicationUser.setLastName(lastName);

            userRepository.save(applicationUser);
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token  = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (AuthenticationException ex) {
            return new LoginResponseDTO(null, "");
        }
    }
}
