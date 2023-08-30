package com.pathways.services;

import com.pathways.models.ApplicationUser;
import com.pathways.models.UserPermission;
import com.pathways.repository.UserPermissionRepository;
import com.pathways.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private UserPermissionRepository userPermissionRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder encoder,
            UserPermissionRepository userPermissionRepository
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApplicationUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<String> getUserPermissions(String username) {
        Optional<ApplicationUser> user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<UserPermission> userPermissions = userPermissionRepository.findByUser(user);
        return userPermissions.stream()
                .map(userPermission -> userPermission.getPermission().getRoute())
                .collect(Collectors.toList());
    }

    public List<String> getUserPermissions(Integer userId) {
        Optional<ApplicationUser> user = userRepository.findById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<UserPermission> userPermissions = userPermissionRepository.findByUser(user);
        return userPermissions.stream()
                .map(userPermission -> userPermission.getPermission().getRoute())
                .collect(Collectors.toList());
    }

    public void resetPassword(String email, String password) {
        ApplicationUser user = userRepository.findByEmail(email);
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

//    public void initiatePasswordReset(String email) {
//        Optional<ApplicationUser> user = userRepository.findByEmail(email);
//        if (user.isPresent()) {
//            String resetToken = this.generateResetToken();
//            user.setResetToken(resetToken);
//            userRepository.save(user);
//
//            sendPasswordResetEmail(user.getEmail(), resetToken);
//        }
//    }

    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
