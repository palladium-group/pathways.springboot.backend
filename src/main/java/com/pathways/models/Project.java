package com.pathways.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String icon;
    private String color;

    @JsonManagedReference
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectLink> projectLinks = new ArrayList<>();

    public Project() {
        super();
    }

    public Project(String name, String icon, String color) {
        super();
        this.name = name;
        this.icon = icon;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ProjectLink> getProjectLinks() {
        return projectLinks;
    }

    public void setProjectLinks(List<ProjectLink> projectLinks) {
        this.projectLinks = projectLinks;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
