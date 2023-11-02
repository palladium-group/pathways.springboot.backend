package com.pathways.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private UUID keyCloakUserId;

    private String route;

    public UserPermission(UUID keyCloakUserId, String route) {
        super();
        this.keyCloakUserId = keyCloakUserId;
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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public UUID getKeyCloakUserId() {
        return keyCloakUserId;
    }

    public void setKeyCloakUserId(UUID keyCloakUserId) {
        this.keyCloakUserId = keyCloakUserId;
    }
}
