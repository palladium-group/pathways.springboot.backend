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
}
