package com.pathways.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name = "projectlink_id")
    private ProjectLink projectLink;

    public UserPermission(ApplicationUser user, ProjectLink projectLink) {
        super();
        this.user = user;
        this.projectLink = projectLink;
    }

    public UserPermission() {
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public ProjectLink getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(ProjectLink projectLink) {
        this.projectLink = projectLink;
    }
}
