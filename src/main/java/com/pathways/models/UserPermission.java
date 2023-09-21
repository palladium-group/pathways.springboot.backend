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

    private String route;

    public UserPermission(ApplicationUser user, String route) {
        super();
        this.user = user;
        this.route = route;
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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
