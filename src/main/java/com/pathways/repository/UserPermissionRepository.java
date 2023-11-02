package com.pathways.repository;

import com.pathways.models.ApplicationUser;
import com.pathways.models.Permission;
import com.pathways.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {
    List<UserPermission> findByKeyCloakUserId(UUID userId);
    Optional<UserPermission> findByKeyCloakUserIdAndRoute(UUID userId, String route);
}
