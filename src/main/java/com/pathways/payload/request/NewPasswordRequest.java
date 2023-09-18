package com.pathways.payload.request;

import jakarta.validation.constraints.NotBlank;

public class NewPasswordRequest {
    @NotBlank
    private String newPassword;
    @NotBlank
    private String resetToken;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
