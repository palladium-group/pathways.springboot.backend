package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserPermissionsRequest {
    private Integer userId;

    private List<String> assignedRoutes;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getAssignedRoutes() {
        return assignedRoutes;
    }

    public void setAssignedRoutes(List<String> assignedRoutes) {
        this.assignedRoutes = assignedRoutes;
    }
}
