package com.pathways.services;

import com.pathways.models.ApplicationUser;
import com.pathways.models.Permission;
import com.pathways.models.UserPermission;
import com.pathways.repository.PermissionRepository;
import com.pathways.repository.UserPermissionRepository;
import com.pathways.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionService {
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private UserPermissionRepository userPermissionRepository;

    public UserPermissionService(
            UserRepository userRepository,
            PermissionRepository permissionRepository,
            UserPermissionRepository userPermissionRepository
    ) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    public void assignPermissionToUser(Integer userId, List<String> permissions) {
        Optional<ApplicationUser> user = userRepository.findById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Remove all previous user permissions first
        List<UserPermission> allUserPermissions = userPermissionRepository.findByUser(user);
        for (UserPermission perm: allUserPermissions) {
            userPermissionRepository.delete(perm);
        }

        for (String route: permissions) {
            // Check if the UserPermission already exists for the given user and permission
            Optional<UserPermission> existingUserPermission = userPermissionRepository.findByUserAndRoute(user.get(), route);

            if (!existingUserPermission.isPresent()) {
                // Save the UserPermission only if it doesn't exist already
                UserPermission userPermission = new UserPermission();
                userPermission.setUser(user.get());
                userPermission.setRoute(route);
                userPermissionRepository.save(userPermission);
            }
        }
    }
}
