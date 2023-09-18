package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProjectLinkRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String url;
    private String color;
    @NotNull
    private Integer projectId;
    @NotNull
    private Integer projectLinkId;

    public UpdateProjectLinkRequest(String name, String url, String color, Integer projectId, Integer projectLinkId) {
        this.name = name;
        this.url = url;
        this.color = color;
        this.projectId = projectId;
        this.projectLinkId = projectLinkId;
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

    public Integer getProjectLinkId() {
        return projectLinkId;
    }

    public void setProjectLinkId(Integer projectLinkId) {
        this.projectLinkId = projectLinkId;
    }
}
