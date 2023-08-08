package com.pathways.services;

import com.pathways.models.Permission;
import com.pathways.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }
}
