package com.pathways.payload.response;

import com.pathways.models.ApplicationUser;

public class LoginResponseDTO {
    private ApplicationUser user;
    private String accessToken;

    public LoginResponseDTO() {
        super();
    }

    public LoginResponseDTO(ApplicationUser user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public ApplicationUser getUser() {
        return this.user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
