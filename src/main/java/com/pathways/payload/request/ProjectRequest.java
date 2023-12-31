package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ProjectRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String icon;
    private String color;

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
}
