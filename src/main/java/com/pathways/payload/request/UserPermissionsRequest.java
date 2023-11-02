package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public class UserPermissionsRequest {
    private UUID userId;

    private List<String> assignedRoutes;

    public List<String> getAssignedRoutes() {
        return assignedRoutes;
    }

    public void setAssignedRoutes(List<String> assignedRoutes) {
        this.assignedRoutes = assignedRoutes;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
