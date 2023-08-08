package com.pathways.controllers;

import com.pathways.models.Permission;
import com.pathways.payload.request.UserPermissionsRequest;
import com.pathways.services.PermissionService;
import com.pathways.services.UserPermissionService;
import com.pathways.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    private PermissionService permissionService;
    private UserPermissionService userPermissionService;
    private UserService userService;

    public PermissionController(PermissionService permissionService, UserPermissionService userPermissionService, UserService userService) {
        this.permissionService = permissionService;
        this.userPermissionService = userPermissionService;
        this.userService = userService;
    }

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermission();
    }
    @GetMapping("/getUserPermissionsById/{userId}")
    public List<String> getUserPermissionsById(@PathVariable Integer userId) {
        return userService.getUserPermissions(userId);
    }
    @PostMapping("/assign-user-permissions")
    public ResponseEntity<String> assignUserPermission(@Valid @RequestBody UserPermissionsRequest userPermissionsRequest) {
        try {
            userPermissionService.assignPermissionToUser(userPermissionsRequest.getUserId(), userPermissionsRequest.getAssignedRoutes());
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
