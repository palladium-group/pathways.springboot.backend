package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProjectRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String icon;
    private String color;
    @NotNull
    private Integer projectId;

    public UpdateProjectRequest(String name, String icon, String color, Integer projectId) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
