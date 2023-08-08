package com.pathways.payload.response;

import com.pathways.models.ApplicationUser;

import java.util.Optional;

public class LoggedInUserResponseDTO {
    private Optional<ApplicationUser> user;

    public LoggedInUserResponseDTO() {
        super();
    }

    public LoggedInUserResponseDTO(Optional<ApplicationUser> user) {
        this.user = user;
    }

    public Optional<ApplicationUser> getUser() {
        return this.user;
    }

    public void setUser(Optional<ApplicationUser> user) {
        this.user = user;
    }
}
