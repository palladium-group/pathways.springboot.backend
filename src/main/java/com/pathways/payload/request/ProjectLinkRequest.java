package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectLinkRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String url;
    @NotBlank
    private String color;
    @NotNull
    private Integer projectId;

    public ProjectLinkRequest(String name, String url, String color, Integer projectId) {
        this.name = name;
        this.url = url;
        this.color = color;
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
